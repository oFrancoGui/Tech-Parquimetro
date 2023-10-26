package com.fiap.techparquimetro.controller;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.service.ReciboService;
import com.fiap.techparquimetro.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recibo")
public class ReciboController {

    @Autowired
    public ReciboService reciboService;

  @PostMapping("/{codigo}")
  public Recibo  criar(@RequestBody String codigo){
  return this.reciboService.criarRecibo(codigo);
  }
}
