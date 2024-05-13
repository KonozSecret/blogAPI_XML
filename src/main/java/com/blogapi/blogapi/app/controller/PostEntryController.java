package com.blogapi.blogapi.app.controller;

import org.springframework.http.ResponseEntity;

public class PostEntryController {

    @Autowired
    private PostEntryService forumEntryService;

    @PostMapping("/forum")
    public ResponseEntity<String> createForumEntry(@RequestBody ForumEntry forumEntry) {
        boolean created = forumEntryService.createForumEntry(forumEntry);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Forum-Eintrag erfolgreich erstellt");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Benutzer nicht angemeldet");
        }
    }
}
