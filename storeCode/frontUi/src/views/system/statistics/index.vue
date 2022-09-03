<template>
  <div class="app">
    <div class="pic">
      <div id="lineOption"></div>
      <div id="sector"></div>
    </div>
    <div id="sales">123</div>

    <div>
      <h2>
        According to the analysis of various statistics of this store, the
        product <span class="tag">{{ name }}</span> has the best sales, the
        product is under the
        <span class="tag">{{ categoryName }} </span> category, the sales of this
        product is <span class="tag">{{ salesNumber }} </span>, suggest the
        store manager to upload more products under this category, because the
        products under this category menu are the best sellers in the whole
        site, people generally like the price of about RM
        <span class="tag">{{ price }} </span> range of products.
      </h2>
    </div>

    <div v-if="list.length > 0">
      <h2>
        In addition, according to the analysis of the store data, the system
        concluded that the sales of the following products have been 0, the
        reason they may not be liked by the public, suggesting the store manager
        to optimize the products as soon as possible, for example: design more
        attractive product images (more attractive product main images and
        attractive titles can improve the conversion rate in the store) and make
        reasonable adjustments according to the price recommended by the system
        to promote the sales of this product.
      </h2>
      <ul v-for="item in list" :key="item">
        <li>{{ item.storeName }}</li>
      </ul>
    </div>
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
      name: "",
      categoryName: "",
      salesNumber: "",
      price: "",
      data: [],
      list: [],
      version: "3.8.2",
      lineOption: {
        title: {
          text: "Store Category Sales State",
          subtext: "True Data",
          x: "center",
        },
        tooltip: {
          trigger: "axis",
        },
        grid: {
          left: "1%",
          right: "4%",
          bottom: "23%",
          containLabel: true,
        },
        legend: {
          padding: 10,
          tooltip: {
            show: true,
          },
          y: "bottom",
          data: ["xx", "aa", "aa"],
        },
        xAxis: { type: "category", data: null },
        yAxis: { type: "value" },
        series: [
          // {
          // name: 'Actual inspection',
          // data: lineData.y_green,
          // type: 'line',
          // itemStyle: { normal: { color: 'green', lineStyle: { color: 'green' } } },
          // },
          // {
          // name: 'Plan inspection',
          // data: lineData.y_red,
          // type: 'line',
          // itemStyle: { normal: { color: 'red', lineStyle: { color: 'red' } } },
          // },
          // {
          // name: 'Number of missed checks',
          // data: lineData.y_blue,
          // type: 'line',
          // itemStyle: { normal: { color: 'blue', lineStyle: { color: 'blue' } } },
          // },
        ],
      },
      option: {
        title: {
          text: "Menu Product Statistics",
          subtext: "True Data",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          left: "left",
          y: "bottom",
        },
        series: [
          {
            name: "CategoryName",
            type: "pie",
            radius: "50%",
            data: undefined,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      },
      option2: {
        title: {
          text: "Store Sales Statistics",
          subtext: "True Data",
          left: "center",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          // left: "3%",
          // right: "4%",
          // bottom: "3%",
          // left: "20%", //The distance of the grid component from the left side of the container
          // right: "30px", //The distance of the grid component from the right side of the container
          // bottom: "20%", //The distance between the grid component and the bottom margin of the container
          containLabel: true,
        },

        xAxis: [
          {
            type: "category",
            data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: { interval: 0, rotate: 30 },
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            name: "Direct",
            type: "bar",
            barWidth: "60%",
            data: [10, 52, 200, 334, 390, 330, 220],
          },
        ],
      },
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    initView() {},
  },
  mounted() {
    // let this_ = this;
    console.log("mounted");

    index().then((res) => {
      // console.log(this.option.series[0].data);
      this.option.series[0].data = res.data.storeCategoryCounts;
      this.option2.xAxis[0].data = res.data.storeSales.storeName;
      this.option2.series[0].data = res.data.storeSales.storeSalesValue;

      this.$echarts
        .init(document.getElementById("sector"))
        .setOption(this.option);
      this.$echarts
        .init(document.getElementById("sales"))
        .setOption(this.option2);
      console.log("categoryNameList", res.data.lineJsonObject.categoryNameList);
      console.log("raw", this.lineOption.legend.data);
      this.lineOption.legend.data = res.data.lineJsonObject.categoryNameList;
      this.lineOption.xAxis.data = res.data.lineJsonObject.days;
      this.lineOption.series = res.data.lineJsonObject.storeCategorySalesVos;
      this.$echarts
        .init(document.getElementById("lineOption"))
        .setOption(this.lineOption);
      this.suggest = res.data.suggest;
      this.price = res.data.price;
      this.name = res.data.name;
      this.categoryName = res.data.categoryName;
      this.salesNumber = res.data.salesNumber;
      this.list = res.data.salesZeroList;
    });
  },
  created() {
    // this.$echarts.init(document.getElementById('main')).setOption(this.option)
  },
};
</script>

<style>
.tag {
  color: red;
}
.pic {
  display: flex;
  height: 700px;
}
.app {
  padding: 15px;
}

#lineOption {
  flex: 1;
  /* width: 100%;
  height: 800px; */
  margin: 0 auto;
}

#sector {
  flex: 1;
  /* width: 100%;
  height: 800px; */
  margin: 0 auto;
}

#sales {
  text-align: center;
  width: 90%;
  height: 800px;
  margin: 15px auto;
}
</style>
