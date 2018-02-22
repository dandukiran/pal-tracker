package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository teRepo;

    public TimeEntryController(TimeEntryRepository teRepo) {
        this.teRepo = teRepo;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        TimeEntry reEntry = teRepo.create(timeEntry);

        return new ResponseEntity<>(reEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry reEntry = teRepo.find(id);

        if(reEntry != null) {
            return new ResponseEntity<>(reEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reEntry, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> reEntryList = teRepo.list();

        return new ResponseEntity<>(reEntryList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody TimeEntry timeEntry) {

        TimeEntry reEntry = teRepo.update(id, timeEntry);

        if(reEntry != null) {
            return new ResponseEntity<>(reEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reEntry, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        teRepo.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
