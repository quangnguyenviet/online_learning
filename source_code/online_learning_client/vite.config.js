import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

// Dev server chạy ở port 3000 vì backend (SecurityConfig + FRONTEND_URL)
// chỉ cho phép CORS từ http://localhost:3000
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        // CRA (webpack sass-loader) có include src/ vào includePaths nên
        // các file .scss dùng `@use "var.scss"` (không đường dẫn tương đối).
        // Vite cần khai báo thủ công tương đương:
        includePaths: [path.resolve(__dirname, './src')],
      },
    },
  },
  server: {
    port: 3000,
  },
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/setupTests.js',
  },
});
