import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as Highcharts from 'highcharts';
import HC_exporting from 'highcharts/modules/exporting';
import { ToastrService } from 'ngx-toastr';
import { DashboardService } from '../dashboard/dashboard.service';
import { IAnalyticsPalyload } from './anaylytics-payload';

@Component({
  selector: 'app-analytics-chart',
  templateUrl: './analytics-chart.component.html',
  styleUrls: ['./analytics-chart.component.css']
})
export class AnalyticsChartComponent implements OnInit {

  chartOptions: {};
  Highcharts = Highcharts;
  chartOptions1: {};
  Highcharts1 = Highcharts;

  analytics: IAnalyticsPalyload[];
  dates: String[] = [];
  totalOrders: number[] = [];
  totalDelivered: number[] =[];
  totalCancelled: number[] = [];
  totalEarnings: number[] = [];

  constructor(private router: Router,
    private dashboardService: DashboardService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch(){
    this.dashboardService.getAnalytics().subscribe({
      next: data => { this.analytics = data.map(x=> {
        return {
          totalCancelled: x.totalCancelled,
          totalDelivered: x.totalDelivered,
          totalEarning: x.totalEarning,
          totalOrders: x.totalOrders,
          date: x.day + '-'+ x.month + '-'+x.year
        }
      });
      this.analytics.forEach(x=> {
        this.dates.push(x.date);
        this.totalCancelled.push(x.totalCancelled);
        this.totalDelivered.push(x.totalDelivered);
        this.totalOrders.push(x.totalOrders);
        this.totalEarnings.push(x.totalEarning);
      });
      this.loadChart1();
      this.loadChart2();
    },
      error: err => console.log(err)
    });
  }


  loadChart1(){
    this.chartOptions = {
      chart: {
        type: 'area'
      },
      title: {
        text: 'Total Sales'
      },
      subtitle: {
        text: 'Analytics data containing total orders, total deliveries and total cancelled orders.'
      },
      tooltip: {
        split: true,
        valueSuffix: ' Orders'
      },
      credits: {
        enabled: false
      },
      exporting: {
        enabled: true
      },
      xAxis: {
        categories: this.dates,
        tickmarkPlacement: 'on',
        title: {
            enabled: false
        }
    },
    yAxis: {
        title: {
            text: 'Orders'
        }
    },
    plotOptions: {
        area: {
            stacking: 'normal',
            lineColor: '#666666',
            lineWidth: 1,
            marker: {
                lineWidth: 1,
                lineColor: '#666666'
            }
        }
    },
      series: [{
        name: 'Total Orders',
        data: this.totalOrders
      }, {
        name: 'Total Orders Delivered',
        data: this.totalDelivered
      }, {
        name: 'Total Orders Cancelled',
        data: this.totalCancelled
      }]
    };

    HC_exporting(Highcharts);
    
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }

  loadChart2(){
    this.chartOptions1 = {
      chart: {
        type: 'area'
      },
      title: {
        text: 'Total Earnings'
      },
      subtitle: {
        text: 'Analytics data containing total earnings per day.'
      },
      tooltip: {
        split: true,
        valueSuffix: ' Rupees'
      },
      credits: {
        enabled: false
      },
      exporting: {
        enabled: true
      },
      xAxis: {
        categories: this.dates,
        tickmarkPlacement: 'on',
        title: {
            enabled: false
        }
    },
    yAxis: {
        title: {
            text: 'Rupees'
        }
    },
    plotOptions: {
        area: {
            stacking: 'normal',
            lineColor: '#666666',
            lineWidth: 1,
            marker: {
                lineWidth: 1,
                lineColor: '#666666'
            }
        }
    },
      series: [{
        name: 'Total Earning',
        data: this.totalEarnings
      }]
    };

    HC_exporting(Highcharts);
    
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }
}
