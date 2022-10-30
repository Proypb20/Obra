import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'obra',
        data: { pageTitle: 'Obras' },
        loadChildren: () => import('./obra/obra.module').then(m => m.ObraModule),
      },
      {
        path: 'proveedor',
        data: { pageTitle: 'Proveedores' },
        loadChildren: () => import('./proveedor/proveedor.module').then(m => m.ProveedorModule),
      },
      {
        path: 'provincia',
        data: { pageTitle: 'Provincias' },
        loadChildren: () => import('./provincia/provincia.module').then(m => m.ProvinciaModule),
      },
      {
        path: 'subcontratista',
        data: { pageTitle: 'Subcontratistas' },
        loadChildren: () => import('./subcontratista/subcontratista.module').then(m => m.SubcontratistaModule),
      },
      {
        path: 'acopio',
        data: { pageTitle: 'Acopios' },
        loadChildren: () => import('./acopio/acopio.module').then(m => m.AcopioModule),
      },
      {
        path: 'unidad-medida',
        data: { pageTitle: 'Unidades de Medida' },
        loadChildren: () => import('./unidad-medida/unidad-medida.module').then(m => m.UnidadMedidaModule),
      },
      {
        path: 'tarea',
        data: { pageTitle: 'Tareas' },
        loadChildren: () => import('./tarea/tarea.module').then(m => m.TareaModule),
      },
      {
        path: 'tipo-comprobante',
        data: { pageTitle: 'Tipos de Comprobante' },
        loadChildren: () => import('./tipo-comprobante/tipo-comprobante.module').then(m => m.TipoComprobanteModule),
      },
      {
        path: 'detalle-acopio',
        data: { pageTitle: 'Detalles de Acopio' },
        loadChildren: () => import('./detalle-acopio/detalle-acopio.module').then(m => m.DetalleAcopioModule),
      },
      {
        path: 'concepto',
        data: { pageTitle: 'Conceptos' },
        loadChildren: () => import('./concepto/concepto.module').then(m => m.ConceptoModule),
      },
      {
        path: 'transaccion',
        data: { pageTitle: 'Transacciones' },
        loadChildren: () => import('./transaccion/transaccion.module').then(m => m.TransaccionModule),
      },
      {
        path: 'lista-precio',
        data: { pageTitle: 'Listas de Precio' },
        loadChildren: () => import('./lista-precio/lista-precio.module').then(m => m.ListaPrecioModule),
      },
      {
        path: 'detalle-lista-precio',
        data: { pageTitle: 'Detalles Listas de Precio' },
        loadChildren: () => import('./detalle-lista-precio/detalle-lista-precio.module').then(m => m.DetalleListaPrecioModule),
      },
      {
        path: 'cliente',
        data: { pageTitle: 'Clientes' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'adv-pend-rep',
        data: { pageTitle: 'Reporte de Estado de Avance' },
        loadChildren: () => import('./adv-pend-rep/adv-pend-rep.module').then(m => m.AdvPendRepModule),
      },
      {
        path: 'sum-trx-rep',
        data: { pageTitle: 'Reporte de Seguimiento de Obra' },
        loadChildren: () => import('./sum-trx-rep/sum-trx-rep.module').then(m => m.SumTrxRepModule),
      },
      {
        path: 'adv-obra-rep',
        data: { pageTitle: 'Reporte de Avance de Obra' },
        loadChildren: () => import('./adv-obra-rep/adv-obra-rep.module').then(m => m.AdvObraRepModule),
      },
      {
        path: 'movimiento',
        data: { pageTitle: 'Movimientos' },
        loadChildren: () => import('./movimiento/movimiento.module').then(m => m.MovimientoModule),
      },
      {
        path: 'subco-pay-rep',
        data: { pageTitle: 'Reporte de Pagos a Proveedores' },
        loadChildren: () => import('./subco-pay-rep/subco-pay-rep.module').then(m => m.SubcoPayRepModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
