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
import java.time.Duration;
import java.time.LocalTime;
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
        String hora = new SimpleDateFormat("HH:mm").format(dataHoraAtual);
        recibo.setIdVeiculo(idVeiculoRegistrado);
        recibo.setHoraEntrada(hora);
        return this.reciboRepository.save(recibo);
    }

    @Override
    public ResponseEntity<?> finalizaRecibo(String idRecibo) {
        double custoRecibo;
        double custoHorasReais;
        double custoMinutoReais;
        double custoMinutoPorHora;
        double usoHora;
        double usoMinuto;
        try {
            Recibo existeRecibo = this.reciboRepository.findById(idRecibo).orElse(null);
            if (existeRecibo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Recibo não encontrado!");
            }
            Date dataHoraAtualSaida = new Date();
            existeRecibo.setHoraSaida(new SimpleDateFormat("HH:mm").format(dataHoraAtualSaida));

            String entradaHoras = existeRecibo.getHoraEntrada();
            String saidaHoras = new SimpleDateFormat("HH:mm").format(dataHoraAtualSaida);

            int horaEntrada = Integer.parseInt(entradaHoras.substring(0, 2));
            int minutoEntrada = Integer.parseInt(entradaHoras.substring(entradaHoras.length() - 2));

            int horaSaida = Integer.parseInt(saidaHoras.substring(0, 2));
            int minutoSaida = Integer.parseInt(saidaHoras.substring(saidaHoras.length() - 2));


            if (minutoEntrada > minutoSaida) {
                usoMinuto = ((60 - minutoEntrada) + minutoSaida);
                custoMinutoPorHora = usoMinuto / 60;
                custoMinutoReais = custoMinutoPorHora * 15;
                usoHora = horaSaida - horaEntrada - 1;
                custoHorasReais = usoHora * 15;
                custoRecibo = custoHorasReais + custoMinutoReais;

            } else {
                usoMinuto = minutoEntrada - minutoSaida;
                custoMinutoPorHora = usoMinuto / 60;
                custoMinutoReais = custoMinutoPorHora * 15;
                usoHora = horaSaida - horaEntrada;
                custoHorasReais = usoHora * 15;

            }
            custoRecibo = custoHorasReais + custoMinutoReais;
            existeRecibo.setValorRecibo(custoRecibo);
            this.reciboRepository.save(existeRecibo);
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
                .orElseThrow(() -> new IllegalArgumentException("Artigo nao existente"));
    }
}
