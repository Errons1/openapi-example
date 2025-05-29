import react from '@vitejs/plugin-react'
import {defineConfig} from 'vite'
import viteTsconfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
    base: '/',
    plugins: [react(), viteTsconfigPaths()],
    server: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
            }
        }
    },
    build: {
        outDir: "../resources/webui",
        emptyOutDir: true
    }
})
