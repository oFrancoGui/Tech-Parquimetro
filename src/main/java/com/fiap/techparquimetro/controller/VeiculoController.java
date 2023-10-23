package com.fiap.techparquimetro.controller;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.model.Veiculo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recibo")
public class VeiculoController {

    @GetMapping
   public List<Veiculo> obterTodos(){
       return null;
   }

   @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo){
        return null;
   }


}
