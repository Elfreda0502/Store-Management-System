<template>
  <div class="app-container">
    <!-- <div id="sector">
    </div>
    <div id="sales">

    </div>
    <h2>
      System suggest : {{suggest}}
    </h2> -->
    <h3 style="font-weight: 100;text-align: justify;">The system will be a web-based smart store management system
      application. With the help of this application, it can help retail store managers to accomplish the easiest and
      most effective store management, thus helping retail store managers to improve management efficiency and
      performance. The retail store manager can manage the store order information, purchase information, inventory
      information, user information, etc. in real time and accurately. In addition, the system will have features such
      as store delivery and store pickup management, which allows store consumers to shop at any time and in any way. In
      addition, the proposed system is also optimized for inventory management functions, for example, information about
      stock items can be entered and updated in real time through the system, as well as the ability to replenish and
      return goods from suppliers through the system and strictly control every step of the process. This helps retail
      business managers or store owners to manage store inventory more easily and gives them a clearer picture of the
      status of their stores. </h3>
    <h3 class="header">User Guide:</h3>
    <h3 class="header">
      <span>I.</span>
      <span style="margin-left: 20px">User Management</span>
    </h3>
    <ul>
      <li>Staff Management - Here we can manage store staff information, including their user ID, username, nickname,
        department, phone number, status, and creation time, and we can also perform other operations.</li>
      <li>User Address Management - Here we can manage the user's address, including the store customer's address, also
        we can add, modify, delete, export operations.</li>
      <li>Role Management - In the role management interface, it includes ordinary user, super administrator and
        merchant roles. They indicate the types of roles that can be created. In addition, we can also add, modify,
        delete, and export roles.</li>
    </ul>


    <h3 class="header">
      <span>II.</span>
      <span style="margin-left: 20px">Institutional Management</span>
    </h3>
    <ul>
      <li>Department Management - It manages the departments of the general store organization, such as the R&D
        department, the financial department, and the human resources department, here I can also add new departments,
        or delete, modify, query departments</li>
      <li>Job Management - We can manage the related job types of store staff, such as Store Owner, Store Manager,
        Cashier, Sales clerk, Warehouse Clerk, here we can also add new Jobs, or modify and query Jobs</li>
    </ul>

    <h3 class="header">
      <span>III.</span>
      <span style="margin-left: 20px">Purchase Management</span>
    </h3>
    <ul>
      <li>All Products (Customer View) - Here we can view all products from the user's purchase perspective, including
        product prices, pictures, and we can classify and retrieve products, and this information is synchronized with
        the All Products in the customer mall interface of.</li>
      <li>Order Management - Manage the orders submitted by customers. When a customer submits an order in the store,
        the background will query the purchase information of the customer just now. In addition, we can also query the
        corresponding order information according to some conditions.</li>
    </ul>


    <h3 class="header">
      <span>IV.</span>
      <span style="margin-left: 20px">Inventory Management</span>
    </h3>
    <ul>
      <li>Product Type - Manage the types of goods in the inventory, here I can also add new categories, or modify the
        types of goods in the inventory, query</li>
      <li>Product List - This represents a list of products. Unlike All Product Customer View, we can view the detailed
        information of all products, including Product ID, product image, product name, product category, price, etc.
        Management, after the administrator uploads the product, the customer user can see the product that has just
        been uploaded to the front desk in the interface of the shopper store and perform a purchase operation.</li>
      <li>Supplier Resource Management - Here we can manage the purchase, return, and exchange of goods from suppliers.
        For example, adding new purchasing information from suppliers, or modifying, deleting, and querying purchasing
        information of commodities.</li>
    </ul>

    <h3 class="header">
      <span>V.</span>
      <span style="margin-left: 20px">System Management</span>
    </h3>
    <ul>
      <li>Message Management - If the store manager has activated the message function, then the customer user can leave
        a message with the merchant on the interface of the shopper store. In addition, the store manager can also reply
        to the message sent by the customer through reply message. Which also facilitates the communication between
        customers, users and businesses. In addition, we can also modify, delete and query it.</li>
      <li>Notification - For the notification function, if the store manager has enabled the notification function, then
        system users can publish new notifications through the system and be seen by other employees of the store, and
        other users can access this function. Notice to browse and access.</li>
    </ul>

    <h3 class="header">
      <span>VI.</span>
      <span style="margin-left: 20px">System Tool</span>
    </h3>
    <ul>
      <li>Menu Management - For menu management, it is a tool store admin through which new tool menu categories can be
        added or removed.</li>
      <li>Dictionary Management - It is a system tool for developers, it represents the correspondence of database table
        field values, we can add or delete new fields, or retrieve fields according to field names, which is very
        convenient.</li>
    </ul>

    <p style="float:right;">Copyright © 2022 by Yu Yue 1001956834</p>



  </div>
</template>

<script>
import { index } from "@/api/store/product";
// import echarts from 'echarts'
export default {
  name: "Index",
  data() {
    return {
      suggest: "",
      data: [],
      version: "3.8.2",
      option: {
        title: {
          text: 'Menu Product Statistics',
          subtext: 'True Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'CategoryName',
            type: 'pie',
            radius: '50%',
            data: undefined,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      option2:{
        title: {
          text: 'Store Sales Statistics',
          subtext: 'True Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
          }
        ]
      }
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    initView() {

    }
  },
  mounted() {
    // let this_ = this;
    console.log('mounted');

    index().then(res => {
      // console.log(this.option.series[0].data);
      this.option.series[0].data = res.data.storeCategoryCounts
      this.option2.xAxis[0].data = res.data.storeSales.storeName
      this.option2.series[0].data = res.data.storeSales.storeSalesValue
      this.$echarts.init(document.getElementById('sector')).setOption(this.option)
      this.$echarts.init(document.getElementById('sales')).setOption(this.option2)
      this.suggest = res.data.suggest
    })
  },
  created() {
    // this.$echarts.init(document.getElementById('main')).setOption(this.option)
  }
};
</script>

<style scoped lang="scss">
li {
  font-size: 18px;
}

#sector {
  width: 100%;
  height: 800px;
  margin: 0 auto;
}
#sales {
  width: 100%;
  height: 600px;
  margin: 0 auto;
}

.header {
  font-weight: bold;
}

.circle {
  background-color: #000;
  height: 10px;
  width: 10px;
  border-radius: 50%;
}

.app-container {
  //word-break:break-all;
}

.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans",
  "Helvetica Neue",
  Helvetica,
  Arial,
  sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h3 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

