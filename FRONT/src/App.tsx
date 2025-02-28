import { Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home/Home'
import CreatePost from './pages/CreatePost/CreatePost'
import Navbar from '@_components/Navbar/Navbar'
import Footer from '@_components/Footer/Footer'
import Aside from '@_components/Aside/Aside'
import useLoadingStore from './stores/useLoadingStore';
import LoadingModal from '@_components/LoadingModal/LoadingModal';
import Post from './pages/Post/Post';
import Toast from './pages/CreatePost/components/Toast/Toast'
import MyPage from './pages/MyPage/MyPage'
import SignIn from './pages/Auth/SignIn'
import SignUp from './pages/Auth/SignUp'

function App() {
  const { isLoading } = useLoadingStore();
  return (
    <>
      <Aside />
      <Navbar />
      <Routes>
        <Route path='/:page?' element={<Home />} />
        <Route path='/create-post' element={<CreatePost />} />
        <Route path='/post/:postId' element={<Post />} />
        <Route path='mypage' element={<MyPage />} />
        <Route path='/signin' element={<SignIn />} />
        <Route path='/signup' element={<SignUp />} />
      </Routes>
      <Footer />
      <LoadingModal isLoading={isLoading} />
      <Toast />
    </>
  )
}

export default App
