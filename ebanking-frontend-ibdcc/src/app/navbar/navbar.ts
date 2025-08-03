import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../services/auth-service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {
constructor(public  authService: AuthService, private router: Router) {
}
  handleLogOut() {
  this.authService.logout();
  this.router.navigateByUrl("/login");
  }
}
