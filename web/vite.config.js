import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  //本地运行配置，以及反向代理配置
  server: {
    host: "0.0.0.0",
    https: false,//是否启用 http 2
    cors: true,//为开发服务器配置 CORS , 默认启用并允许任何源
    open: true,//服务启动时自动在浏览器中打开应用
    port: "9000",
    // 反向代理配置
   // proxy: {
    //  "/api": {
     //   target: "http://localhost:8080",
     //   changeOrigin: true,
     //   pathRewrite: {
    //      "^/api": "",
    //    },
    //  },
   // }
  }
})

