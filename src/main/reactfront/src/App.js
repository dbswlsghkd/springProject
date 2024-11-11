import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/Login/Login';
import Articles from "./components/articles";
import IndexArticles from "./components/articles/new"
import ArticleShow from "./components/articles/show"
import ArticleEdit from "./components/articles/edit"
import PartTable from "./components/masterData/Parts";
import PartnersManagement from "./components/masterData/Partner";
import UserManagement from "./components/masterData/Users";


const App = () => {
  return (
      <BrowserRouter>
        <Routes>
            {/* 기본 페이지를 로그인 페이지로 설정 */}
            <Route path="/" element={<Login />} />
            <Route path="/login" exact element={<Login></Login>}></Route>
            <Route path="/articles" exact element={<Articles></Articles>}></Route>
            <Route path="/articles/:id" element={<ArticleShow></ArticleShow>}></Route>
            <Route path="/articles/:id/edit" element={<ArticleEdit></ArticleEdit>}></Route>
            <Route path="/articles/new" exact element={<IndexArticles></IndexArticles>}></Route>
            <Route path="/parts" exact element={<PartTable></PartTable>}></Route>
            <Route path="/partners" exact element={<PartnersManagement></PartnersManagement>}></Route>
            <Route path="/users" exact element={<UserManagement></UserManagement>}></Route>
        </Routes>
      </BrowserRouter>
  );
};

export default App;
