import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      pageTitle: '¡Página de error!',
    },
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: '¡Página de error!',
      errorMessage: 'No tiene permisos para acceder a la página.',
    },
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      pageTitle: '¡Página de error!',
      errorMessage: 'La página no existe.',
    },
  },
  {
    path: '**',
    redirectTo: '/404',
  },
];
