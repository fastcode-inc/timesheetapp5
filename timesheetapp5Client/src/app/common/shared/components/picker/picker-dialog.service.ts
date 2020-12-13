import { Injectable } from '@angular/core';
import { Globals } from '../../globals';
import { MatDialog, MatDialogRef } from '@angular/material';
import { PickerComponent } from '../picker/picker.component';

export interface IFCDialogConfig {
  Title: string;
  IsSingleSelection?: boolean;
  DisplayField: string;
}
@Injectable({
  providedIn: 'root',
})
export class PickerDialogService {
  isSmallDeviceOrLess: boolean;
  dialogRef: MatDialogRef<any>;
  smallDeviceOrLessDialogSize: string = '100%';
  largerDeviceDialogWidthSize: string = '50%';
  largerDeviceDialogHeightSize: string = '85%';
  constructor(private global: Globals, public dialog: MatDialog) {
    this.global.isSmallDevice$.subscribe((value) => {
      this.isSmallDeviceOrLess = value;
      if (this.dialogRef)
        this.dialogRef.updateSize(
          value ? this.smallDeviceOrLessDialogSize : this.largerDeviceDialogWidthSize,
          value ? this.smallDeviceOrLessDialogSize : this.largerDeviceDialogHeightSize
        );
    });
  }
  open(config: IFCDialogConfig): MatDialogRef<any> {
    this.dialogRef = this.dialog.open(PickerComponent, {
      data: config,
      disableClose: true,
      height: '100%',
      width: this.isSmallDeviceOrLess ? this.smallDeviceOrLessDialogSize : this.largerDeviceDialogWidthSize,
      maxWidth: 'none',
      panelClass: 'picker-modal-dialog',
    });
    return this.dialogRef;
  }
}
