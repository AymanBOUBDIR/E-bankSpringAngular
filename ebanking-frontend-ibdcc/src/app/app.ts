import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Navbar} from './navbar/navbar';
import {ReactiveFormsModule} from '@angular/forms';
import {AuthService} from './services/auth-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ReactiveFormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App  implements OnInit {
  protected title = 'ebanking-frontend-ibdcc';
  constructor(private authService: AuthService) {
  }
  ngOnInit() {

    this.authService.loadJwtFromLocalStorage();
  }
}
