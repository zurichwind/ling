

<template>
  <div style="text-align: center;margin: 0 20px">
    <div style="text-align: center;margin-top: 100px">
      <div style="font-size: 25px">注册新用户</div>
      <div style="font-size: 14px">欢迎注册</div>
    </div>
    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules"  @validate="onValidate" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password"  type="password" placeholder="密码" >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" type="password_repeat" placeholder="重复密码" >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email"  type="email" placeholder="请输入邮箱地址" >
            <template #prefix>
              <el-icon><Message /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :gutter="10">
            <el-col :span="18">
              <el-input v-model="form.code" type="text" placeholder="请输入验证码">
                <template #prefix>
                  <el-icon><EditPen /></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="6">
              <el-button style="width: 100%" type="success" @click="validateEmail">获取验证码</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>

        </el-form-item>
      </el-form>




    </div>
    <div style="margin-top: 10px">

    </div>
    <div style="margin-top: 80px">
      <el-button style="width: 150px" type="warning" @click="register">立即注册</el-button>
    </div>
    <div style="font-size: 14px;line-height: 15px;margin-top: 20px">
      已有账号? <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">
      立即登录</el-link>
    </div>
  </div>
</template>


<script setup>

import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive,ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})



const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please input the username'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
    callback(new Error('用户名不能包含特殊字符,只能包含中英文'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please repeat input the password'))
  } else if (value !== form.password){
    callback(new Error('两次输入密码不一样'))
  } else {
    callback()
  }
}


const rules = {
  username: [
    { validator: validateUsername, trigger: ['blur', 'change']},
    { min: 4, max: 16, message: '不低于四位', trigger: ['blur', 'change']},
  ],
  password: [
    { required: true, message: 'Please input Password', trigger: 'blur' },
    { min: 6, max: 16, message: '不低于六位', trigger: ['blur', 'change']},
  ],
  password_repeat: [
    { validator: validatePassword, trigger: ['blur', 'change']},
  ],
  email: [
    { required: true, message: 'Please input Email', trigger: 'blur' },
    {
      type: 'email',
      message: 'Please input correct email address',
      trigger: ['blur', 'change'],
    },
  ],
  code: [
    { required: true, message: 'Please input Code', trigger: 'blur' },
  ]
}

const formRef = ref()

const isEmailValid = ref(false)

const onValidate = (prop, isValid) => {
  if (prop === 'email')
    isEmailValid.value = isValid
}

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {

    } else {
      ElMessage.warning('请填写完整')
    }
  })
}

const validateEmail = () => {
  post('/auth/valid-email', {
    email: form.email
  },(message) =>{
    ElMessage.success(message)
  })
}

</script>

<style scoped>

</style>