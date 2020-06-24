import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../../services/alert.service';
import {PowerStation} from '../../../models/powerStations';
import {PowerStationService} from '../../../services/power-station.service';

@Component({
    selector: 'app-open-create-power-station',
    templateUrl: './open-create-power-station.component.html',
    styleUrls: ['./open-create-power-station.component.css']
})
export class OpenCreatePowerStationComponent implements OnInit {
    @Input() isAdmin: boolean;
    @Input() create: boolean;
    @ViewChild('createModelTemplate') template: TemplateRef<any>;
    @Output() clickButton = new EventEmitter<void>();

    modalRef: BsModalRef;
    orderForm: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private modalService: BsModalService,
                private alertService: AlertService, private service: PowerStationService) {
    }

    ngOnInit(): void {
    }

    openUpdateModal(powerStation: PowerStation) {
        console.log(powerStation);
        this.create = false;
        this.orderForm = this.formBuilder.group({
            'id': [{value: powerStation.id, disabled: true}],
            'name': [{value: powerStation.name, disabled: false}, Validators.maxLength(1000)],
            'power': [{value: powerStation.power, disabled: false}, Validators.required]
        });
        this.modalRef = this.modalService.show(this.template, {ignoreBackdropClick: true});
    }

    openCreateModal() {
        this.create = true;
        this.orderForm = this.formBuilder.group({
            'id': [{value: 'null', disabled: true}],
            'name': [{value: '', disabled: false}, Validators.maxLength(1000)],
            'power': [{value: '', disabled: false}, Validators.required]
        });
        this.modalRef = this.modalService.show(this.template, {ignoreBackdropClick: true});
    }

    onCreate() {
        if (this.orderForm.invalid) {
            return;
        }
        if (this.create) {
            const order = <PowerStation> this.orderForm.getRawValue();
            console.log(order);
            this.service.save(order).subscribe();
            this.modalRef.hide();
            this.alertService.success(`Elektrownia zaktualizowana.`);
            this.clickButton.emit();
        } else {
            const order = <PowerStation> this.orderForm.getRawValue();
            console.log(order);
            this.service.update(order, order.id).subscribe();
            this.modalRef.hide();
            this.alertService.success(`Elektrownia zaktualizowana.`);
            this.clickButton.emit();
        }

    };
}
