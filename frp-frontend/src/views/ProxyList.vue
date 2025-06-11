<template>
  <div class="proxy-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>FRP 端口映射列表</span>
          <el-button type="primary" size="small" @click="showDialog = true" style="float: right">
            新增端口映射
          </el-button>
        </div>
      </template>

      <el-table :data="proxyList" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="localIp" label="本地IP" width="150" />
        <el-table-column prop="localPort" label="本地端口" width="120" />
        <el-table-column prop="remotePort" label="远程端口" width="120" />
        <el-table-column prop="customDomains" label="自定义域名" />
      </el-table>
    </el-card>

    <el-dialog v-model="showDialog" title="新增端口映射" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="TCP" value="tcp" />
            <el-option label="HTTP" value="http" />
            <el-option label="HTTPS" value="https" />
          </el-select>
        </el-form-item>
        <el-form-item label="本地IP" prop="localIp">
          <el-input v-model="form.localIp" />
        </el-form-item>
        <el-form-item label="本地端口" prop="localPort">
          <el-input-number v-model="form.localPort" :min="1" :max="65535" />
        </el-form-item>
        <el-form-item label="远程端口" prop="remotePort" v-if="form.type === 'tcp'">
          <el-input-number v-model="form.remotePort" :min="1" :max="65535" />
        </el-form-item>
        <el-form-item label="自定义域名" prop="customDomains" v-if="form.type !== 'tcp'">
          <el-input v-model="form.customDomains" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { fetchFrpProxyList, addFrpProxyConfig } from '@/api/frp';
import type { FrpProxy, FrpProxyForm } from '@/types/frp';
import { ElMessage } from 'element-plus';

const proxyList = ref<FrpProxy[]>([]);
const loading = ref(false);
const showDialog = ref(false);

const form = reactive<FrpProxyForm>({
  name: '',
  type: 'tcp',
  localIp: '127.0.0.1',
  localPort: 0,
  remotePort: null,
  customDomains: '',
});

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  localIp: [{ required: true, message: '请输入本地 IP', trigger: 'blur' }],
  localPort: [{ required: true, message: '请输入本地端口', trigger: 'blur' }],
};

const formRef = ref();

const loadData = async () => {
  loading.value = true;
  try {
    proxyList.value = await fetchFrpProxyList();
  } finally {
    loading.value = false;
  }
};

const submit = async () => {
  await formRef.value?.validate();
  try {
    await addFrpProxyConfig(form);
    ElMessage.success('新增成功');
    showDialog.value = false;
    await loadData();
  } catch (e) {
    ElMessage.error('新增失败');
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.proxy-list {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
