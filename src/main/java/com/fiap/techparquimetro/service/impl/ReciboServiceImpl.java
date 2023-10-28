package com.fiap.techparquimetro.service.impl;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.repository.ReciboRepository;
import com.fiap.techparquimetro.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.PersistentMBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReciboServiceImpl implements ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private VeiculoServiceImpl veiculoService;

    @Autowired
    private ReciboServiceImpl reciboService;


    @Override
    public List<Recibo> onterTodos() {
        return this.reciboRepository.findAll();
    }

    @Override
    public Recibo criarRecibo(String idVeiculo) {
        // optei por nao salvar o objeto Veiculo
        String idVeiculoRegistrado = veiculoService.obterVeiculoPorCodigo(idVeiculo).getCodigo();
        Recibo recibo = new Recibo();
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        recibo.setIdVeiculo(idVeiculoRegistrado);
        recibo.setHoraEntrada(hora);
        return this.reciboRepository.save(recibo);
    }

    @Override
    public ResponseEntity<?> finalizaRecibo(String idRecibo){
        try {

            Recibo existeRecibo = this.reciboRepository.findById(idRecibo).orElse(null);

            if (existeRecibo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Artigo não encontrado na coleção!");
            }

            //defini um valor qualquer para testar o update


            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            existeRecibo.setHoraSaida(hora);

            //calcular hora precisa fazer um trambite aqui,defini um valor qualquer para testar o update
            BigDecimal valorRecibo = new BigDecimal(56.00) ;
            existeRecibo.setValorRecibo(valorRecibo);

            this.reciboRepository.save(existeRecibo);
            System.out.print(existeRecibo);
            return ResponseEntity.status(HttpStatus.OK).build();



        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar artigo: " + e.getMessage());
        }
    }


    @Override
    public Recibo obterPorCodigo(String codigo) {
        return this.reciboRepository
                .findById(codigo)
                .orElseThrow(()-> new IllegalArgumentException("Artigo nao existente"));
    }
}
