package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Memo;
import com.example.demo.repository.MemoRepository;

@RestController
@RequestMapping("/api/memos")
public class MemoController {
    
    @Autowired
    private MemoRepository memoRepository;
    
    // 全てのメモを取得
    @GetMapping("/memos")
    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }
    
    // IDによるメモ取得
    @GetMapping("/memos/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Long id) {
        Optional<Memo> memo = memoRepository.findById(id);
        if (memo.isPresent()) {
            return ResponseEntity.ok(memo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 新規メモ作成
    @PostMapping("/memos")
    public ResponseEntity<Memo> createMemo(@RequestBody Memo memo) {
        try {
            Memo savedMemo = memoRepository.save(memo);
            return new ResponseEntity<>(savedMemo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // メモ更新
    @PutMapping("/memos/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Long id, @RequestBody Memo memo) {
        Optional<Memo> memoData = memoRepository.findById(id);
        
        if (memoData.isPresent()) {
            Memo _memo = memoData.get();
            _memo.setTitle(memo.getTitle());
            _memo.setContent(memo.getContent());
            return new ResponseEntity<>(memoRepository.save(_memo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // メモ削除
    @DeleteMapping("/memos/{id}")
    public ResponseEntity<HttpStatus> deleteMemo(@PathVariable Long id) {
        try {
            memoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}