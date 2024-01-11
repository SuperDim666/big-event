import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': { // obtain all requests whose path contains '/api'
        target: 'http://localhost:8080', // source of back-side service
        changeOrigin: true, // change source
        // substitute '/api' into empty string
        rewrite: (path)=>path.replace(/^\/api/, '')
      }
    }
  }
})
