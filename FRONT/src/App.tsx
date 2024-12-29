import { Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home/Home'
import CreatePost from './pages/CreatePost/CreatePost'
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
        <Route path='/create-post' element={<CreatePost />} />
      </Routes>
      <Footer />
    </>
  )
}

export default App
