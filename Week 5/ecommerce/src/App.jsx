// import './App.css'
import Navbar from './components/Navbar.jsx';
import Cart from './pages/Cart.jsx';
import Products from './pages/Products.jsx';
import Home from './pages/Home.jsx';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {

  return (
    <>
    <Router>
      <div className = "container">
        <Navbar />
        <div className='container'>
        
        <Routes>
          <Route path ='/' element={<Home />} />
          <Route path='/products' element = {<Products/>} />
          <Route path='/cart' element = {<Cart/>} />
        </Routes>
        
        </div>
      </div>
    </Router>
    </>
  )
}

export default App
