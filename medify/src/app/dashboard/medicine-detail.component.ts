import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DashboardService } from './dashboard.service';
import { IMedToStore } from './medToStore';

@Component({
  selector: 'app-medicine-detail',
  templateUrl: './medicine-detail.component.html',
  styleUrls: ['./medicine-detail.component.css']
})
export class MedicineDetailComponent implements OnInit {

  medToStore: IMedToStore;
  constructor(private route:ActivatedRoute,
              private dashboardService: DashboardService ) { }

  ngOnInit(): void {
    //getting id from route
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.dashboardService.getMedicineDetails(id).subscribe({
      next: data=> {console.log(data); this.medToStore = data},
      error: err=> console.log(err)
    });
  }

}
