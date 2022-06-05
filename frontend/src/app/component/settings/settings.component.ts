import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { BoardSize } from 'src/app/entity/board-size';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  form: UntypedFormGroup;

  @Output() closeEvent = new EventEmitter<boolean>();
  @Output() saveValues = new EventEmitter<BoardSize>();
  @Input() size: BoardSize | undefined;

  constructor(private fb: UntypedFormBuilder) { 
    this.form = this.fb.group({
      width: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      height: ['', [Validators.required, Validators.pattern("^[0-9]*$")]]
    });
  }

  ngOnInit(): void {
    if(this.size) {
      this.form.controls.width.setValue(this.size.width);
      this.form.controls.height.setValue(this.size.height);
    }
  }

  close() {
    this.closeEvent.emit(true);
  }

  set() {
    this.saveValues.emit({width: this.form.controls.width.value, height: this.form.controls.height.value});
  }
}
