import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from 'react-router'
import App from './App.tsx'
import {Pets} from "./pets/pets.tsx";


createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/pets" element={<Pets/>}/>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
)

