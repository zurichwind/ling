<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="flex: 1">
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://img1.imgtp.com/2023/07/13/KIxTE9y1.png" />
    </div>
    <div style="position: absolute;bottom: 30px;left: 30px;">
      <div>欢迎来到我们的学习平台</div>
    </div>
    <div style="width: 400px;background-color: white">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear">
          <component :is="Component" />
        </transition>
      </router-view>
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;

}
</style>