import { HttpClient } from '@angular/common/http';
import { Token } from '@angular/compiler/src/ml_parser/lexer';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationRequest } from './entity/authentication-request';
import { RegistrationRequest } from './entity/registration-request';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiServerUrl = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public authenticate(request: AuthenticationRequest):  Observable<Token> {
    return this.http.post<Token>(`${this.apiServerUrl}/authenticate`, request);
  }

  public register(request: RegistrationRequest):  Observable<Token> {
    return this.http.post<Token>(`${this.apiServerUrl}/register`, request);
  }
}
