<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="User Id" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="Please enter  the user ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Message" prop="leavingMessage">
        <el-input
          v-model="queryParams.leavingMessage"
          placeholder="Please enter  the message"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Reply message" prop="replyMessage">
        <el-input
          v-model="queryParams.replyMessage"
          placeholder="Please enter  reply message"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Message time" prop="leavingTime">
        <el-date-picker clearable
          v-model="queryParams.leavingTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="Please select a message time">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Reply time" prop="replyTime">
        <el-date-picker clearable
          v-model="queryParams.replyTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="Please select the reply time">
        </el-date-picker>
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
          v-hasPermi="['web:leaving:add']"
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
          v-hasPermi="['web:leaving:edit']"
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
          v-hasPermi="['web:leaving:remove']"
        >Delete</el-button>
      </el-col>










      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="leavingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Id" align="center" prop="id" />
      <el-table-column label="User Id" align="center" prop="uid" />
      <el-table-column label="Message" align="center" prop="leavingMessage" />
      <el-table-column label="Reply message" align="center" prop="replyMessage" />
      <el-table-column label="Message time" align="center" prop="leavingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.leavingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Reply time" align="center" prop="replyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.replyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['web:leaving:edit']"
          >Modify</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['web:leaving:remove']"
          >Delete</el-button>
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


    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div style="display: flex;flex-direction: column">
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item label="User Id" prop="uid">
          <el-input v-model="form.uid" placeholder="Please enter  the user ID" />
        </el-form-item>
        <el-form-item label="Message" prop="leavingMessage">
          <el-input v-model="form.leavingMessage" placeholder="Please enter  the message" />
        </el-form-item>
        <el-form-item label="Reply message" prop="replyMessage">
          <el-input v-model="form.replyMessage" placeholder="Please enter  reply message" />
        </el-form-item>
        <el-form-item label="Message time" prop="leavingTime">
          <el-date-picker clearable
            v-model="form.leavingTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="Please select a message time">
          </el-date-picker>
        </el-form-item>








      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Save</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLeaving, getLeaving, delLeaving, addLeaving, updateLeaving } from "@/api/leaving/leaving";

export default {
  name: "Leaving",
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      leavingList: [],

      title: "",

      open: false,

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uid: null,
        leavingMessage: null,
        replyMessage: null,
        leavingTime: null,
        replyTime: null
      },

      form: {},

      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true;
      listLeaving(this.queryParams).then(response => {
        this.leavingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // CancelButton
    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        id: null,
        uid: null,
        leavingMessage: null,
        replyMessage: null,
        leavingTime: null,
        replyTime: null
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "add a message";
    },

    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLeaving(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "ModifyMessage";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLeaving(this.form).then(response => {
              this.$modal.msgSuccess("Modified successfully");
              this.open = false;
              this.getList();
            });
          } else {
            addLeaving(this.form).then(response => {
              this.$modal.msgSuccess("Insert successfully");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('Whether to confirm to delete the message number is"' + ids + '"data item？').then(function() {
        return delLeaving(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Deletion successfully");
      }).catch(() => {});
    },

    handleExport() {
      this.download('web/leaving/export', {
        ...this.queryParams
      }, `leaving_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
