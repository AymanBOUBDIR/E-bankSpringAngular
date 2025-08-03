import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginForm!: FormGroup;
constructor(private fb: FormBuilder,private authService: AuthService, private router: Router) {
}
ngOnInit() {
  this.loginForm = this.fb.group({
    username: this.fb.control(''),
    password: this.fb.control('')
  })
}

  handleLogin() {
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe({
      next: data => {
       this.authService.loadProfile(data);
       this.router.navigateByUrl("/admin")
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
