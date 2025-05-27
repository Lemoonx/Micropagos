package com.nspTECH.pagos_facturacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nspTECH.pagos_facturacion.DTO.pagoPedidoDTO;
import com.nspTECH.pagos_facturacion.DTO.pedidoDTO;
import com.nspTECH.pagos_facturacion.model.pago;
import com.nspTECH.pagos_facturacion.services.pagoServices;


@RestController
@RequestMapping("/api/v1/Pagos")


public class pagoController {

    @Autowired

    private pagoServices pagoservice;

    @GetMapping
    public ResponseEntity<?> listarPagos(){
        List<pago> pagos = pagoservice.BuscarTodoPago();
        if (pagos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran dato");
        } else {
            return ResponseEntity.ok(pagos);
        }
    }
    @GetMapping("/{ID_PAGO}")
    public ResponseEntity<?> BuscarPago(@PathVariable Long ID_PAGO){

        try {
            pago pagoBuscado = pagoservice.BuscarUnPago(ID_PAGO);
            return ResponseEntity.ok(pagoBuscado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran el pago");
        }
        
    }

@GetMapping("/PagoPedido/{ID_PEDIDO}")
    public ResponseEntity<?> pagoCliente(@PathVariable Long ID_PEDIDO){

        try {
            pago pagoBuscado = pagoservice.BuscarUnPago(ID_PEDIDO);
            pedidoDTO pedido = pagoservice.buscarpedido(ID_PEDIDO);
            pagoPedidoDTO pagopedido = new pagoPedidoDTO();
            pagopedido.setVALOR_TOTAL(pedido.getVALOR_TOTAL());
            pagopedido.setANOTACIONES(pedido.getANOTACIONES());
            pagopedido.setIVA(pedido.getIVA());
            pagopedido.setID_PAGO(pagoBuscado.getID_PAGO());
            pagopedido.setID_PEDIDO(pedido.getID_PEDIDO());
            return ResponseEntity.ok(pagopedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el pago");
        }
        
    }



    @PostMapping
    public ResponseEntity<?> GuardarPago(@RequestBody pago pagoGuardar){
    try {
            pago pagoRegistrar = pagoservice.GuardarPago(pagoGuardar);
            return ResponseEntity.ok(pagoRegistrar);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede registrar el Producto");
    }
    }
    
    @DeleteMapping("/{ID_PAGO}")
        public ResponseEntity<String> EliminarUsuario(@PathVariable Long ID_PAGO){
            try {
                pago pagooBuscado = pagoservice.BuscarUnPago(ID_PAGO);
                pagoservice.EliminarPago(ID_PAGO);
                return ResponseEntity.status(HttpStatus.OK).body("Se elimina pago");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("pago no esta registrado");
            }
        }
    @PutMapping("/{ID_PAGO}") //SOLO PERMITE ACTUALIZAR ESCRIBIENDO TODOS LOS DATOS
        
    public ResponseEntity<?> ActualizarPago(@PathVariable Long ID_USUARIO, @RequestBody pago pagoActualizar){
        try {
            pago pagoActualizado = pagoservice.BuscarUnPago(ID_USUARIO);
            pagoActualizado.setID_PAGO(pagoActualizar.getID_PAGO());
            pagoservice.GuardarPago(pagoActualizar);
            return ResponseEntity.ok(pagoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no esta registrado");
        }
    }
    





}
