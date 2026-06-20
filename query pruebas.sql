select * from Factura
select * from ItemsFactura
select * from CuentaCorriente
select * from MovimientoCtaCte
select * from Remito
select * from ItemRemito

select * from Pedido
select * from ItemPedido
select * from OrdenDePedido
select * from ItemOrdenDePedido
select * from OrdenDeCompra
select * from ItemsOrdenDeCompra
select * from ArticulosDeposito

delete from MovimientoCtaCte
delete from ItemsFactura
delete from Factura
delete from ItemRemito
delete from Remito
delete from ItemPedido
delete from ItemOrdenDePedido
delete from ItemsOrdenDeCompra
delete from OrdenDeCompra
delete from OrdenDePedido
update ArticulosDeposito set idPedido=null
update ArticulosDeposito set estado='DISPONIBLE'
delete from pedido

update ubicacion set estado='DISPONIBLE'
update ubicacion set estado='OCUPADA' where idUbicacion='A010111'
update ubicacion set estado='OCUPADA' where idUbicacion='A010115'
delete from ArticulosDeposito where idArticuloDeposito>8
delete from Lote where idLote>4
update Pedido set estado='APROBADO_EN_ESPERA_STOCK'
delete from ItemOrdenDePedido where idItemOp>39
update OrdenDeCompra set estado='PENDIENTE'


select * from Ubicacion
select * from ArticulosDeposito
select * from Articulo
select * from Lote
select * from Proveedor
select * from ProveedoresArticulos

/*

insert into ArticulosDeposito values (1,'DISPONIBLE', 1, 'A010115',null)
insert into ArticulosDeposito values (1,'DISPONIBLE', 1, 'A010115',null)
insert into ArticulosDeposito values (1,'DISPONIBLE', 1, 'A010115',null)

*/


--query inicializar
delete from MovimientoStock
delete from MovimientoCtaCte
delete from ItemsFactura
delete from Factura
delete from ItemRemito
delete from Remito
delete from ItemPedido
delete from ItemOrdenDePedido
delete from ItemsOrdenDeCompra
delete from OrdenDeCompra
delete from OrdenDePedido
update ArticulosDeposito set idPedido=null
delete from ArticulosDeposito
delete from pedido
delete from Lote
update ubicacion set estado='DISPONIBLE'

DBCC CHECKIDENT ('[ArticulosDeposito]', RESEED, 0);
DBCC CHECKIDENT ('[Lote]', RESEED, 0);
DBCC CHECKIDENT ('[ItemOrdenDePedido]', RESEED, 0);
DBCC CHECKIDENT ('[ItemPedido]', RESEED, 0);
DBCC CHECKIDENT ('[ItemsOrdenDeCompra]', RESEED, 0);
DBCC CHECKIDENT ('[OrdenDeCompra]', RESEED, 0);
DBCC CHECKIDENT ('[OrdenDePedido]', RESEED, 0);
DBCC CHECKIDENT ('[Pedido]', RESEED, 0);
DBCC CHECKIDENT ('[Factura]', RESEED, 8);
DBCC CHECKIDENT ('[ItemsFactura]', RESEED, 0);
DBCC CHECKIDENT ('[Remito]', RESEED, 0);
DBCC CHECKIDENT ('[ItemRemito]', RESEED, 0);
DBCC CHECKIDENT ('[MovimientoStock]', RESEED, 0);
DBCC CHECKIDENT ('[MovimientoCtaCte]', RESEED, 0);


--despues de inicializar, insertar los base para los 11 casos a evaluar

select * from Cliente
select * from Articulo
select * from Ubicacion

select * from ArticulosDeposito
select * from Pedido
select * from ItemPedido
select * from OrdenDePedido
select * from ItemOrdenDePedido
select * from OrdenDeCompra
select * from ItemsOrdenDeCompra

select * from Factura
select * from ItemsFactura
select * from Remito
select * from ItemRemito

select * from Lote
select * from MovimientoStock


insert into Lote values(getdate())

--1
/*Cliente 1 pedido 1*/ insert into ArticulosDeposito values(1,'DISPONIBLE',1,'A010101',null)
/*Cliente 1 pedido 2*/ insert into ArticulosDeposito values(2,'DISPONIBLE',1,'A010101',null)
/*Cliente 4 pedido 3*/ insert into ArticulosDeposito values(3,'DISPONIBLE',1,'A010101',null)
update Ubicacion set estado='OCUPADA' where idUbicacion='A010101'

--2
/*Cliente 3 pedido 4 sin credito*/ insert into ArticulosDeposito values(3,'DISPONIBLE',1,'A010101',null) --queda 30 en ubicacion 1

--3
/*Cliente 3 pedido 5 sin credito*/ --se rechaza y termina ahi

--4
/*Cliente 2 pedido 6 -- con dos faltantes sobre 3*/
--pide articulo 7 y tiene stock. pide articulo 4 (compra fija 20) y articulo 5 (compra fija 40) pero no hay stock
--se emiten las OP pero no las OC
insert into ArticulosDeposito values(7,'DISPONIBLE',1,'A010101',null) --completa la ubicacion 1

--5
/*Cliente 5 pedido 7 -- con 2 faltantes sobre 4 y un faltante si pidio en el 4*/
--pide articulo 4 (1 unidad, su compra fija es 20 por ende alcanza al ser pedido antes)
--pide articulo 13 (sin stock, su compra fija 10)
--pide articulo 7 y articulo 12 y ambos tienen stock
insert into ArticulosDeposito values(7,'DISPONIBLE',1,'A010102',null)
insert into ArticulosDeposito values(12,'DISPONIBLE',1,'A010102',null) --queda 50 en ubicacion 2
update Ubicacion set estado='OCUPADA' where idUbicacion='A010102'

--6
/*Cliente 4 pedido 8 -- con 3 faltantes sobre 5 y un faltante es del punto 4 y el otro faltante del punto 5*/
--pide articulo 10 y articulo 11 y ambos tienen stock
insert into ArticulosDeposito values(10,'DISPONIBLE',1,'A010102',null)
insert into ArticulosDeposito values(11,'DISPONIBLE',1,'A010102',null) --queda 30 en ubicacion 2
--pide articulo 4 del punto 4 (1 unidad, compra fija 20 por ende alcanza) -- se reservan 3 unidades
--pide articulo 13 del punto 5 (1 unidad, compra fija 10 por ende alcanza) -- se reservan 2 unidades
--pide articulo 8 (sin stock, su compra fija es 10)


--7
/*Cliente 3 pedido 9 sin credito y se autoriza por ser insistente */
-- no dice cuanto pide asumo que pide 1 de cada uno de los anteriores
-- pide articulo 4, articulo 5, articulo 13, articulo 8 -- todos por 1 unidad


--8
/* generar ordenes de compra */

--9
/* procesar todas las oc y deberian quedar todos los pedidos en despachables */

--10 y 11
/* despachar todo */