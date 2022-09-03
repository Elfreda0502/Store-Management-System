<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- DepartmentData-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="Please enter  the Department name"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--UserData-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
          <el-form-item label="User name" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="Please enter  the user name"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="Phone number" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="Please enter  your mobile number"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="State" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="User status"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Creation time">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="Start date"
              end-placeholder="End date"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
            >Insert</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
            >Modify</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:user:remove']"
            >Delete</el-button>
          </el-col>











          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="User Id" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="User name" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="User nickname" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Department" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Phone number" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />
          <el-table-column label="State" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="Creation time" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="Operation"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >Modify</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >Delete</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>more
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']">Reset Password</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']">Distribute Role</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>


    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >

        <div style="display: flex;flex-direction: column">
          <el-form-item  label="User nickname" prop="nickName">
            <el-input v-model="form.nickName" placeholder="Please enter User's Nickname" maxlength="30" />
          </el-form-item>
        </div>
        <div style="display: flex;flex-direction: column">
          <el-form-item label="Department" prop="deptId">
            <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="Please select attribution Department" />
          </el-form-item>
        </div>
        <div style="display: flex;flex-direction: column">
            <el-form-item label="Phone number" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="Please enter  your mobile number" />
            </el-form-item>
        </div>
        <div style="display: flex;flex-direction: column">
        <el-form-item label="Email" prop="email">
          <el-input v-model="form.email" placeholder="Please enter Mail" maxlength="50" />
        </el-form-item>
        </div>


        <div style="display: flex;flex-direction: column">
            <el-form-item v-if="form.userId == undefined" label="User name" prop="userName">
              <el-input v-model="form.userName" placeholder="Please enter  the user name" maxlength="30" />
            </el-form-item>
        </div>
        <div style="display: flex;flex-direction: column">
            <el-form-item v-if="form.userId == undefined" label="User password" prop="password">
              <el-input v-model="form.password" placeholder="Please enter user password" type="password" maxlength="20" show-password/>
            </el-form-item>
        </div>

        <div style="display: flex;flex-direction: column">
            <el-form-item label="User Gender">
              <el-select v-model="form.sex" placeholder="Please select gender">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
        </div>
        <div style="display: flex;flex-direction: column">
            <el-form-item label="State">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Post">
              <el-select v-model="form.postIds" multiple placeholder="Please select Post">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Role">
              <el-select v-model="form.roleIds" multiple placeholder="Please select Role">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Money">
              <el-input v-model="form.money"  placeholder="Please enter Money"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="Remarks">
              <el-input v-model="form.remark" type="textarea" placeholder="Please enter  content"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">drag files here, or<em>Click to upload</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> Whether to update existing user data
          </div>
          <span>Only xls and xlsx format files are allowed to be imported.</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">Download template</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">Save</el-button>
        <el-button @click="upload.open = false">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: { Treeselect },
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,
      // User表格Data
      userList: null,

      title: "",
      //  Department树选项
      deptOptions: undefined,

      open: false,
      // Department name
      deptName: undefined,
      //  default 密码
      initPassword: undefined,

      dateRange: [],
      // post选项
      postOptions: [],
      // Role选项
      roleOptions: [],

      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // UserImport参数
      upload: {

        open: false,

        title: "",

        isUploading: false,

        updateSupport: 0,

        headers: { Authorization: "Bearer " + getToken() },

        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },

      columns: [
        { key: 0, label: `User Id`, visible: true },
        { key: 1, label: `User name`, visible: true },
        { key: 2, label: `User nickname`, visible: true },
        { key: 3, label: `Department`, visible: true },
        { key: 4, label: `Phone number`, visible: true },
        { key: 5, label: `State`, visible: true },
        { key: 6, label: `Creation time`, visible: true }
      ],

      rules: {
        userName: [
          { required: true, message: "User name Cannot be empty", trigger: "blur" },
          { min: 2, max: 20, message: 'User name length must be between 2 and 20', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: "User's Nickname Cannot be empty", trigger: "blur" }
        ],
        password: [
          { required: true, message: "user password Cannot be empty", trigger: "blur" },
          { min: 5, max: 20, message: 'User password length must be between 5 and 20', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "Please enter correct email address",
            trigger: ["blur", "change"]
          }
        ],
        // phonenumber: [
        //   {
        //     pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        //     message: "Please enter correct phone number",
        //     trigger: "blur"
        //   }
        // ]
      }
    };
  },
  watch: {

    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {

    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },

    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },

    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // User statusModify
    handleStatusChange(row) {
      let text = row.status === "0" ? "Enable" : "Deactivate";
      this.$modal.confirm('确认要"' + text + '""' + row.userName + '"User吗？').then(function() {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },

    handleAdd() {
      this.reset();
      this.getTreeselect();
      getUser().then(response => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "Add User";
        this.form.password = this.initPassword;
      });
    },

    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "Modify User";
        this.form.password = "";
      });
    },

    handleResetPwd(row) {
      this.$prompt('Please enter "' + row.userName + '"new password', "hint", {
        confirmButtonText: "Save",
        cancelButtonText: "Cancel",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "User password length must be between 5 and 20"
      }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("Modified successfully，The new password is：" + value);
          });
        }).catch(() => {});
    },

    handleAuthRole: function(row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },

    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("Insert successfully");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('Confirm to delete User number is"' + userIds + '"Data item for？').then(function() {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    },

    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },

    handleImport() {
      this.upload.title = "UserImport";
      this.upload.open = true;
    },

    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
    },

    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },

    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "Import结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },

    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
