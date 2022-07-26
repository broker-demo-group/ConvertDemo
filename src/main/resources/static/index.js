"use strict";
(() => {
var exports = {};
exports.id = 405;
exports.ids = [405];
exports.modules = {

/***/ 4370:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "T": () => (/* binding */ API_URL)
/* harmony export */ });
/* unused harmony export API_TEST */
const API_URL = "http://45.12.144.105:8080";
const API_TEST = `${API_URL}/system/test`;


/***/ }),

/***/ 6424:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "K": () => (/* binding */ ASSET_CURRENCIES_INFO),
/* harmony export */   "N": () => (/* binding */ SWAPPABLE_CURRENCIES)
/* harmony export */ });
/* harmony import */ var _constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(4370);

const ASSET_CURRENCIES_INFO = _constants__WEBPACK_IMPORTED_MODULE_0__/* .API_URL */ .T + "/asset/currencies";
const SWAPPABLE_CURRENCIES = _constants__WEBPACK_IMPORTED_MODULE_0__/* .API_URL */ .T + "/asset/convert/currencies";


/***/ }),

/***/ 5702:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "p": () => (/* binding */ AccountSelector)
});

// EXTERNAL MODULE: external "react/jsx-runtime"
var jsx_runtime_ = __webpack_require__(997);
// EXTERNAL MODULE: external "react"
var external_react_ = __webpack_require__(6689);
// EXTERNAL MODULE: external "@mui/material"
var material_ = __webpack_require__(5692);
// EXTERNAL MODULE: external "@mui/material/Typography"
var Typography_ = __webpack_require__(7163);
var Typography_default = /*#__PURE__*/__webpack_require__.n(Typography_);
// EXTERNAL MODULE: ./src/api/constants.js
var constants = __webpack_require__(4370);
;// CONCATENATED MODULE: ./src/api/account.js

const getCcyBalance = (ccy, account = null)=>`${constants/* API_URL */.T}/asset/balances?ccy=${ccy}`;

// EXTERNAL MODULE: external "axios"
var external_axios_ = __webpack_require__(2167);
var external_axios_default = /*#__PURE__*/__webpack_require__.n(external_axios_);
;// CONCATENATED MODULE: ./src/components/convert/account-selector.js






const AccountSelector = (props)=>{
    const { balance , fromCoinLabel ="" , setAvailBal  } = props;
    //   const [balance, setBalance] = useState(0.0);
    (0,external_react_.useEffect)(()=>{
        external_axios_default().get(getCcyBalance(fromCoinLabel)).then((res)=>{
            const { availBal  } = res.data[0];
            setAvailBal(availBal ?? 0.0);
        }).catch((err)=>{
            console.error(`error with getting balance: ${err}`);
        });
    }, [
        fromCoinLabel
    ]);
    return /*#__PURE__*/ (0,jsx_runtime_.jsxs)(jsx_runtime_.Fragment, {
        children: [
            /*#__PURE__*/ (0,jsx_runtime_.jsxs)((Typography_default()), {
                variant: "caption",
                children: [
                    "Available: ",
                    balance,
                    " ",
                    fromCoinLabel
                ]
            }),
            /*#__PURE__*/ (0,jsx_runtime_.jsxs)(material_.FormGroup, {
                children: [
                    /*#__PURE__*/ jsx_runtime_.jsx(material_.FormControlLabel, {
                        fontSize: "small",
                        control: /*#__PURE__*/ jsx_runtime_.jsx(material_.Checkbox, {
                            size: "small",
                            disableRipple: true,
                            defaultChecked: true,
                            sx: {
                                height: 10
                            }
                        }),
                        label: /*#__PURE__*/ jsx_runtime_.jsx((Typography_default()), {
                            fontSize: "small",
                            children: "Funding account: 0.0000"
                        })
                    }),
                    /*#__PURE__*/ jsx_runtime_.jsx(material_.FormControlLabel, {
                        fontSize: "small",
                        control: /*#__PURE__*/ jsx_runtime_.jsx(material_.Checkbox, {
                            size: "small",
                            sx: {
                                height: 10
                            },
                            disableRipple: true
                        }),
                        label: /*#__PURE__*/ jsx_runtime_.jsx((Typography_default()), {
                            fontSize: "small",
                            children: "Trading account: 0.0000"
                        })
                    })
                ]
            })
        ]
    });
};


/***/ }),

/***/ 3919:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "Z": () => (/* binding */ ConvertButton)
});

// EXTERNAL MODULE: external "react/jsx-runtime"
var jsx_runtime_ = __webpack_require__(997);
// EXTERNAL MODULE: external "react"
var external_react_ = __webpack_require__(6689);
// EXTERNAL MODULE: external "prop-types"
var external_prop_types_ = __webpack_require__(580);
var external_prop_types_default = /*#__PURE__*/__webpack_require__.n(external_prop_types_);
;// CONCATENATED MODULE: external "@mui/material/Button"
const Button_namespaceObject = require("@mui/material/Button");
var Button_default = /*#__PURE__*/__webpack_require__.n(Button_namespaceObject);
// EXTERNAL MODULE: external "@mui/material/styles"
var styles_ = __webpack_require__(8442);
;// CONCATENATED MODULE: external "@mui/material/Dialog"
const Dialog_namespaceObject = require("@mui/material/Dialog");
var Dialog_default = /*#__PURE__*/__webpack_require__.n(Dialog_namespaceObject);
;// CONCATENATED MODULE: external "@mui/material/DialogTitle"
const DialogTitle_namespaceObject = require("@mui/material/DialogTitle");
var DialogTitle_default = /*#__PURE__*/__webpack_require__.n(DialogTitle_namespaceObject);
;// CONCATENATED MODULE: external "@mui/material/DialogContent"
const DialogContent_namespaceObject = require("@mui/material/DialogContent");
var DialogContent_default = /*#__PURE__*/__webpack_require__.n(DialogContent_namespaceObject);
;// CONCATENATED MODULE: external "@mui/material/DialogActions"
const DialogActions_namespaceObject = require("@mui/material/DialogActions");
var DialogActions_default = /*#__PURE__*/__webpack_require__.n(DialogActions_namespaceObject);
// EXTERNAL MODULE: external "@mui/material/IconButton"
var IconButton_ = __webpack_require__(7934);
var IconButton_default = /*#__PURE__*/__webpack_require__.n(IconButton_);
;// CONCATENATED MODULE: external "@mui/icons-material/Close"
const Close_namespaceObject = require("@mui/icons-material/Close");
var Close_default = /*#__PURE__*/__webpack_require__.n(Close_namespaceObject);
// EXTERNAL MODULE: external "@mui/material/Typography"
var Typography_ = __webpack_require__(7163);
var Typography_default = /*#__PURE__*/__webpack_require__.n(Typography_);
;// CONCATENATED MODULE: ./src/components/convert/convert-button.js












const BootstrapDialog = (0,styles_.styled)((Dialog_default()))(({ theme  })=>({
        "& .MuiDialogContent-root": {
            padding: theme.spacing(2)
        },
        "& .MuiDialogActions-root": {
            padding: theme.spacing(1)
        }
    }));
const BootstrapDialogTitle = (props)=>{
    const { children , onClose , ...other } = props;
    return /*#__PURE__*/ (0,jsx_runtime_.jsxs)((DialogTitle_default()), {
        sx: {
            m: 0,
            p: 2
        },
        ...other,
        children: [
            children,
            onClose ? /*#__PURE__*/ jsx_runtime_.jsx((IconButton_default()), {
                "aria-label": "close",
                onClick: onClose,
                sx: {
                    position: "absolute",
                    right: 8,
                    top: 8,
                    color: (theme)=>theme.palette.grey[500]
                },
                children: /*#__PURE__*/ jsx_runtime_.jsx((Close_default()), {})
            }) : null
        ]
    });
};
BootstrapDialogTitle.propTypes = {
    children: (external_prop_types_default()).node,
    onClose: (external_prop_types_default()).func.isRequired
};
function ConvertButton(props) {
    const { handleCancelCallback , handleConfirmCallback  } = props;
    const [open, setOpen] = external_react_.useState(false);
    const handleClickOpen = ()=>{
        setOpen(true);
    };
    const handleClose = ()=>{
        setOpen(false);
        handleCancelCallback();
    };
    const handleConfirm = ()=>{
        setOpen(false);
        handleConfirmCallback();
    };
    return /*#__PURE__*/ (0,jsx_runtime_.jsxs)("div", {
        children: [
            /*#__PURE__*/ jsx_runtime_.jsx((Button_default()), {
                color: "primary",
                variant: "contained",
                fullWidth: true,
                onClick: handleClickOpen,
                children: "Convert"
            }),
            /*#__PURE__*/ (0,jsx_runtime_.jsxs)(BootstrapDialog, {
                onClose: handleClose,
                "aria-labelledby": "customized-dialog-title",
                open: open,
                children: [
                    /*#__PURE__*/ jsx_runtime_.jsx(BootstrapDialogTitle, {
                        id: "customized-dialog-title",
                        onClose: handleClose,
                        children: "This is an example user warning"
                    }),
                    /*#__PURE__*/ jsx_runtime_.jsx((DialogContent_default()), {
                        dividers: true,
                        children: /*#__PURE__*/ jsx_runtime_.jsx((Typography_default()), {
                            gutterBottom: true,
                            children: "Warn your users of something here, for example, market volatility might cause conversion slippage?"
                        })
                    }),
                    /*#__PURE__*/ (0,jsx_runtime_.jsxs)((DialogActions_default()), {
                        children: [
                            /*#__PURE__*/ jsx_runtime_.jsx((Button_default()), {
                                onClick: handleClose,
                                children: "Cancel"
                            }),
                            /*#__PURE__*/ jsx_runtime_.jsx((Button_default()), {
                                variant: "contained",
                                color: "primary",
                                autoFocus: true,
                                onClick: handleConfirm,
                                children: "Confirm"
                            })
                        ]
                    })
                ]
            })
        ]
    });
};


/***/ }),

/***/ 5577:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {


// EXPORTS
__webpack_require__.d(__webpack_exports__, {
  "O": () => (/* binding */ Estimator)
});

// EXTERNAL MODULE: external "react/jsx-runtime"
var jsx_runtime_ = __webpack_require__(997);
// EXTERNAL MODULE: external "react"
var external_react_ = __webpack_require__(6689);
// EXTERNAL MODULE: external "@mui/material"
var material_ = __webpack_require__(5692);
// EXTERNAL MODULE: external "@mui/material/Typography"
var Typography_ = __webpack_require__(7163);
var Typography_default = /*#__PURE__*/__webpack_require__.n(Typography_);
// EXTERNAL MODULE: external "axios"
var external_axios_ = __webpack_require__(2167);
// EXTERNAL MODULE: ./src/api/constants.js
var constants = __webpack_require__(4370);
;// CONCATENATED MODULE: ./src/api/convert.js

const ESTIMATE_QUOTE = `${constants/* API_URL */.T}/asset/convert/estimate-quote`;

;// CONCATENATED MODULE: ./src/components/convert/estimator.js






function estimateQuoteBody(fromCoin, toCoin) {
    console.log(fromCoin);
    console.log(toCoin);
    return {
        baseCcy: fromCoin,
        quoteCcy: toCoin,
        side: "buy",
        rfqSz: "1",
        rfqSzCcy: toCoin
    };
}
const Estimator = (props)=>{
    const { fromCoinLabel , toCoinLabel  } = props;
    const { 0: ratio , 1: setRatio  } = (0,external_react_.useState)(-1);
    (0,external_react_.useEffect)(()=>{
        fetch(ESTIMATE_QUOTE, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(estimateQuoteBody(fromCoinLabel, toCoinLabel))
        }).then((res)=>res.json()).then((data)=>{
            console.log("Success!");
            console.log(data);
        }).catch((err)=>{
            console.log(`Error: ${err}`);
        });
    }, [
        fromCoinLabel
    ]);
    return /*#__PURE__*/ (0,jsx_runtime_.jsxs)((Typography_default()), {
        variant: "caption",
        children: [
            "Estimated: 1 ",
            toCoinLabel,
            " = ",
            ratio,
            " ",
            fromCoinLabel
        ]
    });
};


/***/ }),

/***/ 3836:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "N": () => (/* binding */ FromCoinField)
/* harmony export */ });
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(997);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(5692);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_mui_material__WEBPACK_IMPORTED_MODULE_1__);


const ariaLabel = {
    "aria-label": "fromCurrency"
};
const FromCoinField = (props)=>{
    const { availBal , coinSelected , swappableCoins , onSelectNewCoin  } = props;
    return /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
        children: [
            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Typography, {
                variant: "body2",
                children: "From"
            }),
            /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                sx: {
                    display: "flex",
                    flexDirection: "row",
                    p: 2,
                    justifyContent: "space-between",
                    border: "1px solid grey"
                },
                children: [
                    /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Input, {
                        error: true,
                        placeholder: "0.00000",
                        inputProps: ariaLabel,
                        disableUnderline: true
                    }),
                    /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                        sx: {
                            display: "flex",
                            flexDirection: "row",
                            alignItems: "center",
                            justifyContent: "flex-end"
                        },
                        children: [
                            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Typography, {
                                variant: "button",
                                align: "right",
                                children: "Max"
                            }),
                            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Typography, {
                                sx: {
                                    marginX: 1
                                },
                                children: "|"
                            }),
                            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Autocomplete, {
                                disablePortal: true,
                                id: "combo-box-demo",
                                options: swappableCoins,
                                renderOption: (props, option)=>/*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                                        component: "li",
                                        sx: {
                                            "& > img": {
                                                mr: 2,
                                                flexShrink: 0
                                            }
                                        },
                                        ...props,
                                        children: [
                                            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("img", {
                                                loading: "lazy",
                                                width: "20",
                                                src: option.logoLink,
                                                alt: ""
                                            }),
                                            option.label
                                        ]
                                    }),
                                value: coinSelected,
                                sx: {
                                    width: 150
                                },
                                renderInput: (params)=>{
                                    params.InputProps.startAdornment = /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.InputAdornment, {
                                        position: "start",
                                        children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("img", {
                                            loading: "lazy",
                                            width: "20",
                                            src: coinSelected.logoLink,
                                            alt: ""
                                        })
                                    });
                                    return /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.TextField, {
                                        ...params
                                    });
                                },
                                onChange: (event, value)=>onSelectNewCoin(value)
                            })
                        ]
                    })
                ]
            })
        ]
    });
};


/***/ }),

/***/ 3675:
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "w": () => (/* binding */ ToCoinField)
/* harmony export */ });
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(997);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(5692);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_mui_material__WEBPACK_IMPORTED_MODULE_1__);


const ariaLabel = {
    "aria-label": "fromCurrency"
};
const swappableCoins = [
    {
        label: "BTC"
    },
    {
        label: "ETH"
    }
];
const ToCoinField = (props)=>{
    const { coinSelected , swappableCoins , onSelectNewCoin  } = props;
    return /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
        children: [
            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Typography, {
                variant: "body2",
                children: "To"
            }),
            /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                sx: {
                    display: "flex",
                    flexDirection: "row",
                    p: 2,
                    justifyContent: "space-between",
                    border: "1px solid grey"
                },
                children: [
                    /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Input, {
                        placeholder: "0.00000",
                        inputProps: ariaLabel,
                        disableUnderline: true
                    }),
                    /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                        sx: {
                            display: "flex",
                            flexDirection: "row",
                            alignItems: "center",
                            justifyContent: "flex-end"
                        },
                        children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Autocomplete, {
                            disablePortal: true,
                            id: "combo-box-demo",
                            options: swappableCoins,
                            renderOption: (props, option)=>/*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_1__.Box, {
                                    component: "li",
                                    sx: {
                                        "& > img": {
                                            mr: 2,
                                            flexShrink: 0
                                        }
                                    },
                                    ...props,
                                    children: [
                                        /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("img", {
                                            loading: "lazy",
                                            width: "20",
                                            src: option.logoLink,
                                            alt: ""
                                        }),
                                        option.label
                                    ]
                                }),
                            value: coinSelected,
                            sx: {
                                width: 150
                            },
                            renderInput: (params)=>{
                                params.InputProps.startAdornment = /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.InputAdornment, {
                                    position: "start",
                                    children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("img", {
                                        loading: "lazy",
                                        width: "20",
                                        src: coinSelected.logoLink,
                                        alt: ""
                                    })
                                });
                                return /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_1__.TextField, {
                                    ...params
                                });
                            },
                            onChange: (event, value)=>onSelectNewCoin(value)
                        })
                    })
                ]
            })
        ]
    });
};


/***/ }),

/***/ 3486:
/***/ ((module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.a(module, async (__webpack_handle_async_dependencies__, __webpack_async_result__) => { try {
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__),
/* harmony export */   "getServerSideProps": () => (/* binding */ getServerSideProps)
/* harmony export */ });
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(997);
/* harmony import */ var react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var next_head__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(968);
/* harmony import */ var next_head__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(next_head__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(5692);
/* harmony import */ var _mui_material__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_mui_material__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _components_dashboard_layout__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(3018);
/* harmony import */ var _components_convert_from_coin_field__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(3836);
/* harmony import */ var _components_convert_to_coin_field__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(3675);
/* harmony import */ var _components_convert_convert_button__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(3919);
/* harmony import */ var _mui_material_IconButton__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(7934);
/* harmony import */ var _mui_material_IconButton__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(_mui_material_IconButton__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var _mui_icons_material_SwapVert__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(4773);
/* harmony import */ var _mui_icons_material_SwapVert__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(_mui_icons_material_SwapVert__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(2167);
/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_9___default = /*#__PURE__*/__webpack_require__.n(axios__WEBPACK_IMPORTED_MODULE_9__);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(6689);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_10___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_10__);
/* harmony import */ var src_api_currencies__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(6424);
/* harmony import */ var _components_convert_account_selector__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(5702);
/* harmony import */ var _components_convert_estimator__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(5577);
var __webpack_async_dependencies__ = __webpack_handle_async_dependencies__([_components_dashboard_layout__WEBPACK_IMPORTED_MODULE_3__]);
_components_dashboard_layout__WEBPACK_IMPORTED_MODULE_3__ = (__webpack_async_dependencies__.then ? (await __webpack_async_dependencies__)() : __webpack_async_dependencies__)[0];














function Customers(props) {
    console.log(props);
    const { currenciesInfo , swappableCurrencies  } = props;
    const { 0: swappableCoins , 1: setSwappableCoins  } = (0,react__WEBPACK_IMPORTED_MODULE_10__.useState)([
        {
            label: "BTC",
            logoLink: ""
        },
        {
            label: "ETH",
            logoLink: ""
        }, 
    ]);
    const { 0: fromCoin , 1: setFromCoin  } = (0,react__WEBPACK_IMPORTED_MODULE_10__.useState)({
        label: "BTC",
        logoLink: ""
    });
    const { 0: toCoin , 1: setToCoin  } = (0,react__WEBPACK_IMPORTED_MODULE_10__.useState)({
        label: "ETH",
        logoLink: ""
    });
    const { 0: availBal , 1: setAvailBal  } = (0,react__WEBPACK_IMPORTED_MODULE_10__.useState)(0);
    (0,react__WEBPACK_IMPORTED_MODULE_10__.useEffect)(()=>{
        console.log(`swappableCurrencies: ${JSON.stringify(swappableCurrencies)}`);
        const updatedSwappableCurrencies = swappableCurrencies.map((e)=>({
                label: e.ccy,
                logoLink: currenciesInfo.find((i)=>e.ccy === i.ccy).logoLink
            }));
        setSwappableCoins(updatedSwappableCurrencies);
        if (updatedSwappableCurrencies.length >= 2) {
            setFromCoin(updatedSwappableCurrencies[0]);
            setToCoin(updatedSwappableCurrencies[1]);
        }
    }, []);
    const swapCoins = ()=>{
        const a = fromCoin;
        const b = toCoin;
        setFromCoin(b);
        setToCoin(a);
    };
    return /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.Fragment, {
        children: [
            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx((next_head__WEBPACK_IMPORTED_MODULE_1___default()), {
                children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx("title", {
                    children: "Convert"
                })
            }),
            /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Box, {
                component: "main",
                sx: {
                    flexGrow: 1,
                    py: 8
                },
                children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Container, {
                    maxWidth: false,
                    children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Box, {
                        component: "main",
                        sx: {
                            alignItems: "center",
                            display: "flex",
                            flexGrow: 1,
                            minHeight: "100%"
                        },
                        children: /*#__PURE__*/ (0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxs)(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Container, {
                            maxWidth: "sm",
                            children: [
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_convert_from_coin_field__WEBPACK_IMPORTED_MODULE_4__/* .FromCoinField */ .N, {
                                    coinSelected: fromCoin,
                                    availBal: availBal,
                                    swappableCoins: swappableCoins,
                                    onSelectNewCoin: setFromCoin
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_convert_account_selector__WEBPACK_IMPORTED_MODULE_12__/* .AccountSelector */ .p, {
                                    balance: availBal,
                                    fromCoinLabel: fromCoin.label,
                                    setAvailBal: setAvailBal
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Box, {
                                    height: 16
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Box, {
                                    sx: {
                                        display: "flex",
                                        justifyContent: "center"
                                    },
                                    children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx((_mui_material_IconButton__WEBPACK_IMPORTED_MODULE_7___default()), {
                                        "aria-label": "switch-currencies",
                                        onClick: swapCoins,
                                        children: /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx((_mui_icons_material_SwapVert__WEBPACK_IMPORTED_MODULE_8___default()), {})
                                    })
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_convert_to_coin_field__WEBPACK_IMPORTED_MODULE_5__/* .ToCoinField */ .w, {
                                    coinSelected: toCoin,
                                    swappableCoins: swappableCoins,
                                    onSelectNewCoin: setToCoin
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_convert_estimator__WEBPACK_IMPORTED_MODULE_13__/* .Estimator */ .O, {
                                    fromCoinLabel: fromCoin.label,
                                    toCoinLabel: toCoin.label
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_mui_material__WEBPACK_IMPORTED_MODULE_2__.Box, {
                                    height: 16
                                }),
                                /*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_convert_convert_button__WEBPACK_IMPORTED_MODULE_6__/* ["default"] */ .Z, {
                                    handleConfirmCallback: ()=>{},
                                    handleCancelCallback: ()=>{}
                                })
                            ]
                        })
                    })
                })
            })
        ]
    });
}
Customers.getLayout = (page)=>/*#__PURE__*/ react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx(_components_dashboard_layout__WEBPACK_IMPORTED_MODULE_3__/* .DashboardLayout */ .c, {
        children: page
    });
const getServerSideProps = async (ctx)=>{
    const promises = [
        axios__WEBPACK_IMPORTED_MODULE_9___default().get(src_api_currencies__WEBPACK_IMPORTED_MODULE_11__/* .ASSET_CURRENCIES_INFO */ .K),
        axios__WEBPACK_IMPORTED_MODULE_9___default().get(src_api_currencies__WEBPACK_IMPORTED_MODULE_11__/* .SWAPPABLE_CURRENCIES */ .N)
    ];
    try {
        const responses = await Promise.all(promises);
        return {
            props: {
                currenciesInfo: responses[0].data.map((e)=>({
                        ccy: e.ccy,
                        logoLink: e.logoLink
                    })),
                swappableCurrencies: responses[1].data
            }
        };
    } catch  {
        return {
            props: {
                currenciesInfo: [],
                swappableCurrencies: []
            }
        };
    }
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (Customers);

__webpack_async_result__();
} catch(e) { __webpack_async_result__(e); } });

/***/ }),

/***/ 1480:
/***/ ((module) => {

module.exports = require("@emotion/styled");

/***/ }),

/***/ 3365:
/***/ ((module) => {

module.exports = require("@mui/icons-material/Menu");

/***/ }),

/***/ 8017:
/***/ ((module) => {

module.exports = require("@mui/icons-material/Search");

/***/ }),

/***/ 4773:
/***/ ((module) => {

module.exports = require("@mui/icons-material/SwapVert");

/***/ }),

/***/ 5692:
/***/ ((module) => {

module.exports = require("@mui/material");

/***/ }),

/***/ 7934:
/***/ ((module) => {

module.exports = require("@mui/material/IconButton");

/***/ }),

/***/ 7163:
/***/ ((module) => {

module.exports = require("@mui/material/Typography");

/***/ }),

/***/ 8442:
/***/ ((module) => {

module.exports = require("@mui/material/styles");

/***/ }),

/***/ 7077:
/***/ ((module) => {

module.exports = require("@mui/material/utils");

/***/ }),

/***/ 2167:
/***/ ((module) => {

module.exports = require("axios");

/***/ }),

/***/ 9888:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/app-router-context.js");

/***/ }),

/***/ 2796:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/head-manager-context.js");

/***/ }),

/***/ 1925:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/i18n/normalize-locale-path.js");

/***/ }),

/***/ 8524:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/is-plain-object.js");

/***/ }),

/***/ 8020:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/mitt.js");

/***/ }),

/***/ 4406:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/page-path/denormalize-page-path.js");

/***/ }),

/***/ 4964:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router-context.js");

/***/ }),

/***/ 1751:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/add-path-prefix.js");

/***/ }),

/***/ 299:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/format-next-pathname-info.js");

/***/ }),

/***/ 3938:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/format-url.js");

/***/ }),

/***/ 9565:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/get-asset-path-from-route.js");

/***/ }),

/***/ 5789:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/get-next-pathname-info.js");

/***/ }),

/***/ 1428:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/is-dynamic.js");

/***/ }),

/***/ 8854:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/parse-path.js");

/***/ }),

/***/ 1292:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/parse-relative-url.js");

/***/ }),

/***/ 4567:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/path-has-prefix.js");

/***/ }),

/***/ 979:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/querystring.js");

/***/ }),

/***/ 3297:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/remove-trailing-slash.js");

/***/ }),

/***/ 6052:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/resolve-rewrites.js");

/***/ }),

/***/ 4226:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/route-matcher.js");

/***/ }),

/***/ 5052:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/router/utils/route-regex.js");

/***/ }),

/***/ 9232:
/***/ ((module) => {

module.exports = require("next/dist/shared/lib/utils.js");

/***/ }),

/***/ 968:
/***/ ((module) => {

module.exports = require("next/head");

/***/ }),

/***/ 1853:
/***/ ((module) => {

module.exports = require("next/router");

/***/ }),

/***/ 580:
/***/ ((module) => {

module.exports = require("prop-types");

/***/ }),

/***/ 6689:
/***/ ((module) => {

module.exports = require("react");

/***/ }),

/***/ 997:
/***/ ((module) => {

module.exports = require("react/jsx-runtime");

/***/ }),

/***/ 5941:
/***/ ((module) => {

module.exports = import("swr");;

/***/ })

};
;

// load runtime
var __webpack_require__ = require("../webpack-runtime.js");
__webpack_require__.C(exports);
var __webpack_exec__ = (moduleId) => (__webpack_require__(__webpack_require__.s = moduleId))
var __webpack_exports__ = __webpack_require__.X(0, [952,664,306,704,18], () => (__webpack_exec__(3486)));
module.exports = __webpack_exports__;

})();