import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RegisterExtendedService } from '../register/register.service';
import { ErrorService, Globals } from 'src/app/common/shared';
import { RegisterComponent } from 'src/app/account/register/register.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterExtendedComponent extends RegisterComponent {
  itemForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public global: Globals,
    public errorService: ErrorService,
    public registerService: RegisterExtendedService
  ) {
    super(formBuilder, router, global, errorService, registerService);
  }
}
