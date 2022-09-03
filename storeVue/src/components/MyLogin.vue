
<template>
  <div id="myLogin">
    <el-dialog title="Login" width="300px" center :visible.sync="isLogin">
      <el-form :model="LoginUser" status-icon ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user-solid" placeholder="Please enter the account number" v-model="LoginUser.name"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <el-input prefix-icon="el-icon-view" type="password" placeholder="Please input a password" v-model="LoginUser.pass"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" type="primary" @click="Login" style="width:100%;">Login</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { mapActions } from "vuex";

export default {
  name: "MyLogin",
  data() {

    return {
      LoginUser: {
        name: "admin",
        pass: "qwelll123"
      },





    };
  },
  computed: {

    isLogin: {
      get() {
        return this.$store.getters.getShowLogin;
      },
      set(val) {
        this.$refs["ruleForm"].resetFields();
        this.setShowLogin(val);
      }
    }
  },
  methods: {
    ...mapActions(["setUser", "setShowLogin"]),
    Login() {

      this.$refs["ruleForm"].validate(valid => {

        if (valid) {
          this.$axios
            .post("http://localhost:9090/login", {
              username: this.LoginUser.name,
              password: this.LoginUser.pass
            })
            .then(res => {
              console.log(res)
              console.log(res.data.code);

              if (res.data.code === 200) {
                 localStorage.setItem("token", res.data.token)
                this.$axios("http://localhost:9090/getInfo", {
                  headers: {
                    "Authorization": res.data.token
                  }
                }).then(res => {
                  if (res.data.code === 200) {
                    console.log(res.data)
                    this.isLogin = false
                    let user = JSON.stringify(res.data.user)
                    localStorage.setItem("user", user)
                    this.setUser(res.data.user)
                    this.notifySucceed(res.data.msg)
                  } else {

                    this.notifyError(res.data.msg)
                  }
                })
              } else {

                this.notifyError(res.data.msg);
              }
            })
            .catch(err => {
              return Promise.reject(err);
            });
        } else {
          return false;
        }
      });
    }
  }
};
</script>
<style>
</style>
