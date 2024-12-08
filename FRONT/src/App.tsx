import { Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home/Home'
import Navbar from '@_components/Navbar/Navbar'
import Footer from '@_components/Footer/Footer'
import Aside from '@_components/Aside/Aside'

function App() {
  return (
    <>
      <Aside />
      <Navbar />
      <Routes>
        <Route path='/:page?' element={<Home />} />
      </Routes>
      <Footer />
    </>
  )
}

export default App
