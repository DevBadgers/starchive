import { Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home/Home'
import CreatePost from './pages/CreatePost/CreatePost'
import Navbar from '@_components/Navbar/Navbar'
import Footer from '@_components/Footer/Footer'
import Aside from '@_components/Aside/Aside'
import useLoadingStore from './store/useLoadingStore';
import LoadingModal from '@_components/LoadingModal/LoadingModal';
import Post from './pages/Post/Post';

function App() {
  const { isLoading } = useLoadingStore();
  return (
    <>
      <Aside />
      <Navbar />
      <Routes>
        <Route path='/:page?' element={<Home />} />
        <Route path='/create-post' element={<CreatePost />} />
        <Route path='/post' element={<Post />} />
      </Routes>
      <Footer />
      <LoadingModal isLoading={isLoading} />
    </>
  )
}

export default App
