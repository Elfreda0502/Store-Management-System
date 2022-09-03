
<template>
  <div class="home" id="home" name="home">

    <div class="block">
      <el-carousel height="460px">
        <el-carousel-item v-for="item in carousel" :key="item.carousel_id">
          <img style="height:460px;width: 100%;" :src="item.imgPath" :alt="item.describes" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="main-box">
      <div class="main">
        <div class="phone">
          <div class="box-hd">
            <div class="title">Recommend</div>
          </div>
          <div class="box-bd">
            <div class="list">
              <MyList :list="phoneList" :isMore="true"></MyList>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      carousel: [
        {
          "imgPath":require('../assets/imgs/rotationMap/1.jpg')
        },
        {
          "imgPath":require('../assets/imgs/rotationMap/2.jpg')
        },
        {
          "imgPath":require('../assets/imgs/rotationMap/3.jpg')
        },
        {
          "imgPath":require('../assets/imgs/rotationMap/4.jpg')
        },
        {
          "imgPath":require('../assets/imgs/rotationMap/5.jpg')
        }
      ],
      phoneList: "",
    };
  },
  created() {

    this.getPromo("游戏", "phoneList",);
  },
  methods: {
    getChildMsg(val) {
      this.applianceActive = val;
    },
    getChildMsg2(val) {
      this.accessoryActive = val;
    },

    getPromo(categoryName, val, api) {
      api = 'http://localhost:9090'+ "/store/product/list?pageNum=10";
      this.$axios
        .get(api)
        .then(res => {
          this[val] = res.data.rows;
        })
        .catch(err => {
          return Promise.reject(err);
        });
    }
  }
};
</script>
<style scoped>
@import "../assets/css/index.css";
</style>
