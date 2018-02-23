package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository teRepo;
    private CounterService counter;
    private GaugeService gauge;

    public TimeEntryController(TimeEntryRepository teRepo, CounterService counter, GaugeService gauge) {
        this.teRepo = teRepo;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        TimeEntry reEntry = teRepo.create(timeEntry);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", teRepo.list().size());

        return new ResponseEntity<>(reEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry reEntry = teRepo.find(id);

        if(reEntry != null) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<>(reEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reEntry, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> reEntryList = teRepo.list();
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(reEntryList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody TimeEntry timeEntry) {

        TimeEntry reEntry = teRepo.update(id, timeEntry);

        if(reEntry != null) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<>(reEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(reEntry, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        teRepo.delete(id);
        counter.increment("TimeEntry.updated");
        gauge.submit("timeEntries.count", teRepo.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
