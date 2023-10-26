package com.fiap.techparquimetro.controller;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.model.Veiculo;
import com.fiap.techparquimetro.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    public VeiculoService veiculoService;

    @GetMapping("/{codigo}")
    public Veiculo obterVeiculoPorCodigo(@PathVariable String codigo){

        return this.veiculoService.obterVeiculoPorCodigo(codigo);
   }

    @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo){

        return this.veiculoService.criar(veiculo);
   }


}
