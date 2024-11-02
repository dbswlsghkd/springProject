import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/Login/Login';
import Articles from "./components/articles";
import IndexArticles from "./components/articles/new";

const App = () => {
  return (
      <BrowserRouter>
        <Routes>
            <Route path="/login" exact element={<Login></Login>}></Route>
            <Route path="/articles" exact element={<Articles></Articles>}></Route>
            <Route path="/articles/new" exact element={<IndexArticles></IndexArticles>}></Route>
        </Routes>
      </BrowserRouter>
  );
};

export default App;