package com.fiap.techparquimetro.service.impl;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.model.Veiculo;
import com.fiap.techparquimetro.repository.ReciboRepository;
import com.fiap.techparquimetro.repository.VeiculoRepository;
import com.fiap.techparquimetro.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReciboServiceImpl implements ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private VeiculoServiceImpl veiculoService;

    @Override
    public List<Recibo> onterTodos() {
        return this.reciboRepository.findAll();
    }

    @Override
    public Recibo criarRecibo(String idVeiculo) {
        Recibo recibo = new Recibo();
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        recibo.setIdVeiculo(idVeiculo);
        recibo.setHoraEntrada(hora);
        return this.reciboRepository.save(recibo);

    }

    @Override
    public Recibo obterPorCodigo(String codigo) {
        return this.reciboRepository
                .findById(codigo)
                .orElseThrow(()-> new IllegalArgumentException("Artigo nao existente"));
    }
}
