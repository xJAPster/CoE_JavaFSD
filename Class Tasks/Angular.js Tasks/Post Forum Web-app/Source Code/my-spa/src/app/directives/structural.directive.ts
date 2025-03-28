import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({
  selector: '[appStructural]'
})
export class StructuralDirective {
  constructor(private templateRef: TemplateRef<any>, private vcr: ViewContainerRef) {}

  @Input() set appStructural(condition: boolean) {
    if (condition) {
      this.vcr.createEmbeddedView(this.templateRef);
    } else {
      this.vcr.clear();
    }
  }
}
