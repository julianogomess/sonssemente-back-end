package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.model.utils.HistHelper;
import com.somsemente.organicos.repository.HistoricoRepository;
import com.somsemente.organicos.service.EmailService;
import com.somsemente.organicos.service.HistoricoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HistoricoServiceImpl implements HistoricoService {

    @Autowired
    HistoricoRepository repository;

    @Autowired
    EmailService emailService;

    @Override
    public List<Historico> findAll() {
        return repository.findAll();
    }

    @Override
    public Historico findById(String id) {
        Optional<Historico> h = repository.findById(id);
        if(h.isPresent()){
            return h.get();
        }
        return null;
    }

    @Override
    public Historico save(Historico historico) {
        return repository.save(historico);
    }

    @Override
    public void delete(Historico historico) {
        repository.delete(historico);
    }

    @Override
    public void sendEmailToClientes(){
        List<HistHelper> lista = repository.getHistByTipo(false, false);
        log.info("A lista consta com : " + lista.size());
        for (HistHelper h:lista){
            if(h.getTotal()>=3){
                List<Historico> historicos = repository.getPesquisasPorEmail(h.get_id(),false);
                String title = "Observamos que você visitou nosso site, Ganhou um CUPOM!!";
                emailService.sendSimpleMessage(h.get_id(),title,bodyMail(historicos));
                posEnvioEmail(historicos);
            }else{
                break;
            }
        }
        log.info("Registros de pesquisas atualizados!");
    }
    private void posEnvioEmail(List<Historico> historicos){
        for(Historico i:historicos ){
            i.setEnviado(true);
            repository.save(i);
        }
    }


    private String bodyMail(List<Historico> historicos){
        String msg = "Nós da Sons Semente observamos sua busca por nossos produtos. \n" +
                "Acreditamos que os preços não te interessaram mas para isso viemos aqui te recompensar\n" +
                "Volte para nosso site é compre com o cupom: TOMA10 \n" +
                "As pesquisas feitas por você foram: ";
        for(Historico i:historicos ){
            if(historicos.lastIndexOf(i)==historicos.size()-1){
                msg+= i.getPesquisa() + ".";
            }else {
                msg+= i.getPesquisa() + ", ";
            }
        }
        return msg;
    }
}
