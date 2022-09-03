<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="Old password" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="Please enter Old password" type="password" show-password/>
    </el-form-item>
    <el-form-item label="New password" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="Please enter New Password" type="password" show-password/>
    </el-form-item>
    <el-form-item label="Confirm password" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="Please confirm the password" type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">Save</el-button>
      <el-button type="danger" size="mini" @click="close">Close</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("The two entered passwords do not match"));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },

      rules: {
        oldPassword: [
          { required: true, message: "Old password Cannot be empty", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "New password cannot be empty", trigger: "blur" },
          { min: 6, max: 20, message: "6 to 20 characters long", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Confirm Password Cannot be empty", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess("Modified successfully");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
