import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/Login/Login';

const App = () => {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" exact element={<Login></Login>} />
          {/* 다른 경로 추가 가능 */}
        </Routes>
      </BrowserRouter>
  );
};

export default App;
