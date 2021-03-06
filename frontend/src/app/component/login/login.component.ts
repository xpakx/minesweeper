import { Token } from '../../entity/token';
import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: UntypedFormGroup;
  public invalid: boolean = false;
  public message: string = '';

  constructor(private fb: UntypedFormBuilder, private service: AuthenticationService, 
    private router: Router) { 
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  toRegister(): void {
    this.router.navigate(["/register"]);
  }

  logIn(): void {
    if(this.form.valid) {
      this.invalid = false;
      this.service.authenticate({
        username: this.form.controls.username.value,
        password: this.form.controls.password.value
      }).subscribe(
        (response: Token) => {
          localStorage.setItem("token", response.token);
          localStorage.setItem("user_id", response.username);
          this.router.navigate([""]);
        },
        (error: HttpErrorResponse) => {
          if(error.status === 401) {
            localStorage.removeItem("token");
          }
          this.message = error.error.message;
          this.invalid = true;
        }
      )
    } else {
      this.message = "Fields cannot be empty!";
      this.invalid = true;
    }
  }

}
