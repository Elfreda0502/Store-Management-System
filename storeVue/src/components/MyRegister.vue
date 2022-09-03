
<template>
  <div id="register">
    <el-dialog title="Register" width="300px" center :visible.sync="isRegister">
      <el-form :model="RegisterUser" status-icon ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="name">
          <el-input prefix-icon="el-icon-user-solid" placeholder="Please enter the account number"
            v-model="RegisterUser.name"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input class="emailBox" prefix-icon="el-icon-message" placeholder="Input email"
            v-model="RegisterUser.email">
          </el-input>
          <el-button style="width: 92px;" @click="sendCOde()" :disabled="disabled" class="sendCode" type="primary"
            size="mini">{{ disabled == true ? num : 'Send Code' }}</el-button>
        </el-form-item>
        <el-form-item prop="code">
          <el-input prefix-icon="el-icon-user-solid" placeholder="Please enter the code" v-model="RegisterUser.code">
          </el-input>
        </el-form-item>

        <el-form-item prop="pass">
          <el-input prefix-icon="el-icon-view" type="password" placeholder="Please input a password"
            v-model="RegisterUser.pass"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input prefix-icon="el-icon-view" type="password" placeholder="Please enter the password again"
            v-model="RegisterUser.confirmPass"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" type="primary" @click="Register" style="width:100%;">Register</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "MyRegister",
  props: ["register"],
  data() {
    return {
      disabled: false,
      isRegister: false,
      RegisterUser: {},
      timer: '',
      num: 60
    }
  },
  watch: {

    register: function (val) {
      if (val) {
        this.isRegister = val;
      }
    },

    isRegister: function (val) {
      if (!val) {
        this.$refs["ruleForm"].resetFields();
        this.$emit("fromChild", val);
      }
    }
  },
  methods: {
    sendCOde() {
      this.$axios
        .get("http://localhost:9090/sendCode?email=" + this.RegisterUser.email)
        .then(res => {
          if (res.data.code === 200) {
            console.log(res);
            this.notifySucceed(res.data.msg)
            this.disabled = true
            this.num = 5
            this.timer = setInterval(this.updateNumber, 1000);
          } else {
            this.notifyError(res.data.msg);
          }
        })
        .catch(err => {
          return Promise.reject(err);
        });

      // alert("Send Success");


    },
    updateNumber() {
      this.num = this.num - 1
      console.log(this.num);
      if (this.num == 0) {
        clearInterval(this.timer);
        this.disabled = false
      }
    },
    Register() {
      this.$axios
        .post("http://localhost:9090/register", {
          username: this.RegisterUser.name,
          password: this.RegisterUser.pass,
          email:this.RegisterUser.email,
          code:this.RegisterUser.code
        })
        .then(res => {

          if (res.data.code === 200) {

            this.isRegister = false;

            this.notifySucceed(res.data.msg);
          } else {

            this.notifyError(res.data.msg);
          }
        })
        .catch(err => {
          return Promise.reject(err);
        });
    }
  }
};
</script>
<style>
.sendCode {
  position: absolute;
  left: 153px;
  top: 6px;
}

.emailBox {
  position: relative;
}
</style>
