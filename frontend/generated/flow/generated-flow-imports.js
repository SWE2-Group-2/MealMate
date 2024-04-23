import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === 'e303a03b1d7825d5c1b3cf16f1200b1cf8fc1557cd8376ea51dfafef3ef8f5e8') {
    pending.push(import('./chunks/chunk-0b7e549215b1ebe8c8799fc5eb23f0f1dcf940d7e209a36d0ea2c04a048b8d85.js'));
  }
  if (key === 'd3ff7d0a0731e18d84eede60dcc115785b03540cc8b8fe9a7086ac45274d3ca8') {
    pending.push(import('./chunks/chunk-bedefaff06a31a2e0b7aca4fba3ac49ccd660deef29be68c573ce1c5014c00a0.js'));
  }
  if (key === 'a09ca0c9845473094a083f0ce268fe8ea478271477dec18c09341c60f684b559') {
    pending.push(import('./chunks/chunk-a860c55cda36fce2849343a3ff2ee3892cfec81613905d960831aeaea89882a5.js'));
  }
  if (key === '1a7cd818db393c9c6ac04a7b17c039fed556809c1f02e5385ce4bc181780b068') {
    pending.push(import('./chunks/chunk-0ec5c7d51f180e4ee2c178afc94974bdd9b166effdc6d3dc880a01dad263ec3e.js'));
  }
  if (key === 'e89522ab899e6f32ede000a2cd55d483c8f85e1b95090764a7342425aae47f4b') {
    pending.push(import('./chunks/chunk-0b7e549215b1ebe8c8799fc5eb23f0f1dcf940d7e209a36d0ea2c04a048b8d85.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}