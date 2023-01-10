import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResObraRepComponent } from './list/res-obra-rep.component';
import { ResObraRepRoutingModule } from './route/res-obra-rep-routing.module';
import { UniquePipe } from './unique.pipe';

@NgModule({
  imports: [SharedModule, ResObraRepRoutingModule],
  declarations: [ResObraRepComponent, UniquePipe],
})
export class ResObraRepModule {}
